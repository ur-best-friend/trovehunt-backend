package com.example.system.services;

import com.example.system.dto.treasure.CreateTreasureDto;
import com.example.system.entity.User;
import com.example.system.services.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImportService {
    private final UserService userService;
    private final TreasureService treasureService;
    private final ImageService imageService;

    @Transactional
    //TODO: refactor this method (does not follow the DRY principle. ?use factory or builder pattern for treasure/user creation)
    public void importUsers(String... args) throws IOException {
        System.out.println("Importing users");
        if(args.length==1) throw new IllegalArgumentException("Please, specify address to .xls file (separator: "+ File.separator+")");
        if(args.length==2) throw new IllegalArgumentException("Please, specify path to user images folder (separator: "+File.separator+")");
        String sheetFilePath = args[1];
        String imagesFolderPath = args[2];

        //https://stackoverflow.com/a/1516154
        POIFSFileSystem fs;
        File sheetFile = new File(sheetFilePath);
        if(!sheetFile.exists()) throw new FileNotFoundException("The system cannot find the file specified ("+sheetFile.toPath()+")");
        fs = new POIFSFileSystem(new FileInputStream(sheetFile));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        int cols = 0; // No of columns
        int tmp = 0;

        for(int i = 1; i < rows; i++) {
            row = sheet.getRow(i);
            if(row != null) {
                tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                if(tmp > cols) cols = tmp;
            }
        }
        HashMap<String,String> importForm;
        ArrayList<HashMap<String,String>> importData = new ArrayList<>();
        for(int r = 1; r < rows; r++) {
            row = sheet.getRow(r);
            if(row != null) {
                String fname,lname = null,username,password,integrationId=null;
                byte[] img;
                for(int c = 0; c < cols; c++) {
                    cell = row.getCell((short)c);
                    if(cell==null) continue;
                    String val = cell.getStringCellValue();
                    switch (c){
                        case 1:
                            if (StringUtils.isEmpty(val)) break;
                            integrationId = val;
                            break;
                        case 4:
                            lname = val;
                            break;
                        case 5:
                            fname = val;
                            if (StringUtils.isEmpty(fname) && StringUtils.isEmpty(lname)) break;
                            username = fname+" "+lname;
                            if(username.trim().isEmpty()) continue;

                            // Generate a random alphanumeric password
                            password = new Random().ints(97 /*letter 'a'*/, 122 /*letter 'z'*/ + 1)
                                        .limit(10) // total string length
                                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                        .toString();

                            File imgFile = new File(imagesFolderPath + File.separator + integrationId + ".jpg");
                            if(!imgFile.exists()) throw new FileNotFoundException("The system cannot find the file specified ("+imgFile.toPath()+")");
                            User saved = userService.createUser(username, password, Files.readAllBytes(imgFile.toPath()), imgFile.getName(), integrationId);
                            System.out.println( saved );
                            importForm = new HashMap<>();
                            importForm.put("username",username);
                            importForm.put("password",password);
                            importForm.put("integrationId",integrationId);
                            importData.add(importForm);
                            break;
                    }
                }
            }
        }
        StringBuilder csvContent = new StringBuilder();
        StringBuilder rowContent = new StringBuilder();
        for (int ri = 0; ri < importData.size(); ri++) {
            importForm = importData.get(ri);
            if(ri==0) csvContent.append("username").append(";").append("password").append(";").append("integrationId").append(";").append("\n");
            rowContent
                    .append(importForm.get("username")).append(";")
                    .append(importForm.get("password")).append(";")
                    .append(importForm.get("integrationId")).append(";")
                    .append("\n");
            csvContent.append(rowContent);
            rowContent = new StringBuilder();
        }

        Files.write(Paths.get("importReport.csv"), csvContent.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Transactional
    //TODO: refactor this method (does not follow the DRY principle. ?use factory or builder pattern for treasure/user creation)
    public void importTreasures(String... args) throws IOException {
        System.out.println("Importing treasures");
        if(args.length==1) throw new IllegalArgumentException("Please, specify address to .xls file (separator: "+ File.separator+")");
        if(args.length==2) throw new IllegalArgumentException("Please, specify path to images folder (separator: "+File.separator+")");
        String sheetFilePath = args[1];
        String imagesFolderPath = args[2];

        //https://stackoverflow.com/a/1516154
        POIFSFileSystem fs;
        File sheetFile = new File(sheetFilePath);
        if(!sheetFile.exists()) throw new FileNotFoundException("The system cannot find the file specified ("+sheetFile.toPath()+")");
        fs = new POIFSFileSystem(new FileInputStream(sheetFile));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        int cols = 0; // No of columns
        int tmp = 0;

        for(int i = 1; i < rows; i++) {
            row = sheet.getRow(i);
            if(row != null) {
                tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                if(tmp > cols) cols = tmp;
            }
        }
        for(int r = 1; r < rows; r++) {
            row = sheet.getRow(r);
            if(row != null) {

                String fName=null, lName=null;
                String uName=null;
                Date date=null;
                double lng=-1d, lat=-1d;
                String[] description=null;
                List<Pair<String, byte[]>> treasureImages = new LinkedList<>();
//                byte[][] images=null;
//                String[] imageFiles=null;

                for(int c = 0; c < cols; c++) {
                    cell = row.getCell((short)c);
                    if(cell==null) continue;
                    String val = cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : null;
                    if(val == null) continue;

                    switch (c) {
                        case 0:
                            lng = Double.parseDouble(val.split(",")[1]);
                            lat = Double.parseDouble(val.split(",")[0]);
                            break;
                        case 1:
                            description = val.split("\\|");
                            break;
                        case 2:
                            String[] splitLinks = val.replaceAll("\\\"","").split(" ");
                            treasureImages.clear();
                            for (int i = 0; i < splitLinks.length; i++) {
                                String namePath = splitLinks[i];
                                System.out.println("UNESCAPED: "+namePath+" ESCAPED: "+"ESCAPED: "+namePath);
                                //https://stackoverflow.com/a/7795850
                                //FIXME: Lazy workaround
                                String namePathEscaped =
                                        namePath.length() < 20 ?
                                                namePath.split("\\.")[0].replaceAll("\\W+", "_")+"."+namePath.split("\\.")[1]
                                                : namePath;
                                File imgFile = new File(imagesFolderPath + File.separator + namePathEscaped);

                                if (!imgFile.exists())
                                    throw new FileNotFoundException("The system cannot find the file specified (" + imgFile.toPath() + ")");

                                // TODO: Make sure the name contains file extension
                                treasureImages.add(Pair.of(imgFile.getName(), Files.readAllBytes(imgFile.toPath())));
                            }
                            break;
                        case 3:
                            date = cell.getDateCellValue();
                            break;
                        case 4:
                            fName = val;
                            break;
                        case 5:
                            lName = val;
                            uName = fName + " " + lName;
                            break;
                    }
                }//For cell loop end
                if(treasureImages.size() == 0) continue;
                System.out.println(uName+" "+date+" "+(lng+":"+lat)+" "+treasureImages.size()+" total images");
                User treasureCreator = userService.getUserByUsername(uName);
                System.out.println("Saving "+r);
                List<String> imgUrls = treasureImages.stream()
                        .map((fnameImgPair)-> {
                            try {
                                return imageService.saveImage(fnameImgPair.getSecond(), fnameImgPair.getFirst(), treasureCreator).getUrl();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .collect(Collectors.toList());

                List<String> descriptionList = description == null ? new ArrayList<>() : new ArrayList<>( Arrays.asList(description) );
                CreateTreasureDto createTreasureDto = new CreateTreasureDto(descriptionList, imgUrls, lat, lng);
                treasureService.createTreasure(treasureCreator, createTreasureDto);
            }
        }
    }
}
