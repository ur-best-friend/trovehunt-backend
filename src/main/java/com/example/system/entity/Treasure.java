package com.example.system.entity;

import com.example.system.dto.geocoder.GeocoderResult;
import com.example.system.jsonviews.TreasureFindingView;
import com.example.system.jsonviews.TreasureView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;


@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Treasure implements SocialLikeEntity {
    public Treasure(User author, double latitude, double longitude, List<String> imageUrls, List<String> textBlocks){
        this.author = author;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrls = imageUrls;
        this.textBlocks = new ArrayList<>(textBlocks);
    }

    @Id
    @Getter
    @GeneratedValue
    private int id;

    @Getter @Setter
    @ElementCollection
    @Column(name = "IMAGE_URLS", length = 4096)
    private List<String> imageUrls;

    @Getter @Setter
    @Column(name = "TEXT_BLOCKS", length = 4096)
    @ElementCollection
    private List<String> textBlocks;

    @Getter
    @Setter
    @OrderColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @Getter
    @ElementCollection
    @CollectionTable(name = "treasure_liked_ids")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Integer> likedIds = new HashSet<>();

    @Getter
    private String formattedLocation;
    @Getter
    @JsonView(TreasureView.CompleteView.class)
    private String city;
    @Getter
    @JsonView(TreasureView.CompleteView.class)
    private String country;
    public void setGeocoderResultData(GeocoderResult geocoderResult) {
        String city = geocoderResult.getCity();
        String country = geocoderResult.getCountry();

        this.city = city;
        this.country = country;

        //TODO: [LOW PRIOR] Rewrite treasure location formatting
        String treasureFormattedLocation = geocoderResult.getFormatted()
                .replaceAll(String.format("%s ,|, %s|%s ,|, %s", city, city, country, country), "");
        this.formattedLocation = treasureFormattedLocation;
    }

    @Getter
    private double latitude;
    @Getter
    private double longitude;

    @Column(updatable = false)
    @CreationTimestamp
    private Date date;
    @JsonView(TreasureView.MinimalView.class)
    public Long getDate(){ return date.getTime(); }

    // TODO: Move reward values to a config file
    //Readonly rewards
    //For creating a treasure
    @JsonView(TreasureView.MinimalView.class)
    public int getRewardCreated() { return 300; }
    //For finding a treasure
    @JsonView(TreasureView.MinimalView.class)
    public int getRewardFound() { return 500; }
    //For other users finding your treasure
    @JsonView(TreasureView.MinimalView.class)
    public int getRewardUser() { return 200; }

    @Getter
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne
    private User author;

    @Getter
    @JsonView({TreasureView.MinimalView.class, TreasureFindingView.HiddenView.class})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OrderColumn
    @OneToMany(mappedBy = "treasure")
    private List<TreasureFinding> userFindings = new ArrayList<>();
}
