package us.nm.state.hsd.codebreaker.model.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import us.nm.state.hsd.codebreaker.configuration.Beans;
import us.nm.state.hsd.codebreaker.service.UUIDStringifier;

@Entity
@Table(
    indexes = @Index(columnList = "created")
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"id", "created", "pool", "length", "guessCount", "solved", "text"})
public class Code {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "code_id", updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  @JsonIgnore
  private UUID id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @Column(nullable = false, updatable = false)
  @NotEmpty
  private String pool;

  @NonNull
  @Column(name = "code_text", nullable = false, updatable = false)
  @JsonIgnore
  private String text;

  @Column(updatable = false)
  @Min(1)
  @Max(20)
  private int length;
  
  @NonNull
  @OneToMany(mappedBy = "code", fetch = FetchType.LAZY, 
  cascade = CascadeType.ALL,  orphanRemoval = true ) 
  @OrderBy("created ASC")
  @JsonIgnore
  private final List<Guess> guesses = new LinkedList<>();
  
  @Transient
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private String key;
  
  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  @NonNull
  public String getPool() {
    return pool;
  }

  public void setPool(@NonNull String pool) {
    this.pool = pool;
  }

  @NonNull
  public String getText() {
    return text;
  }

  public void setText(@NonNull String code) {
    this.text = code;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public List<Guess> getGuesses() {
    return guesses;
  }
  
  public int getGuessCount() {
    return guesses.size();
  }
  
  public int[] codePoints() {
    return(text != null)
        ? text.codePoints().toArray()
            :null;
            
  }
  
  @PostLoad
  @PostPersist
  private void updateKey() {
    UUIDStringifier stringifier = Beans.bean(UUIDStringifier.class);
    key = stringifier.toString(id);
  }
  
}
