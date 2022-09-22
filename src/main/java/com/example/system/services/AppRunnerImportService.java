package com.example.system.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppRunnerImportService implements CommandLineRunner {
    private final ImportService importService;

    public boolean shouldStopAfterInitialization(String... args) {
        return IS_IMPORTING_TREASURES(args) || IS_IMPORTING_USERS(args);
    }

    private boolean IS_IMPORTING_USERS(String[] args){
        if(args.length<1) return false;
        else return args[0].equalsIgnoreCase("user_import");
    }
    private boolean IS_IMPORTING_TREASURES(String[] args){
        if(args.length<1) return false;
        else return args[0].equalsIgnoreCase("treasure_import");
    }

    @Transactional
    //TODO: Automatically create database if it doesn't exist
    public void run(String... args) throws Exception {
        if(IS_IMPORTING_USERS(args)){
            importService.importUsers(args);
            return;
        }
        if(IS_IMPORTING_TREASURES(args)){
            importService.importTreasures(args);
            return;
        }
    }
}
