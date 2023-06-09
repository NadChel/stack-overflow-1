package stack.overflow.model.entity.elastic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import stack.overflow.model.entity.Account;

import java.time.LocalDateTime;

@Document(indexName = "questions")
@NoArgsConstructor
@Getter
@Setter
public class ElasticQuestion implements Persistable<Long> {
    @Id
    private Long id;
    @Field(type = FieldType.Date)
    @CreatedDate
    private LocalDateTime createdDate;
    @Field(type = FieldType.Date)
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Object)
    private Account owner;

    @Override
    public boolean isNew() {
        return id == null ||
                (createdDate == null && modifiedDate == null);
    }
}
