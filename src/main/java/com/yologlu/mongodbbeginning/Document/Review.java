package com.yologlu.mongodbbeginning.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Review")
public class Review implements Serializable {
    private String userName;
    private Integer rating;
    private boolean approved;
}
