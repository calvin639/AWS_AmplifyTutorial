package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Family type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Families", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Family implements Model {
  public static final QueryField ID = field("Family", "id");
  public static final QueryField NAME = field("Family", "Name");
  public static final QueryField AGE = field("Family", "Age");
  public static final QueryField ATTRIBUTE = field("Family", "Attribute");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String Name;
  private final @ModelField(targetType="Int") Integer Age;
  private final @ModelField(targetType="String") String Attribute;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return Name;
  }
  
  public Integer getAge() {
      return Age;
  }
  
  public String getAttribute() {
      return Attribute;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Family(String id, String Name, Integer Age, String Attribute) {
    this.id = id;
    this.Name = Name;
    this.Age = Age;
    this.Attribute = Attribute;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Family family = (Family) obj;
      return ObjectsCompat.equals(getId(), family.getId()) &&
              ObjectsCompat.equals(getName(), family.getName()) &&
              ObjectsCompat.equals(getAge(), family.getAge()) &&
              ObjectsCompat.equals(getAttribute(), family.getAttribute()) &&
              ObjectsCompat.equals(getCreatedAt(), family.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), family.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getAge())
      .append(getAttribute())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Family {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("Name=" + String.valueOf(getName()) + ", ")
      .append("Age=" + String.valueOf(getAge()) + ", ")
      .append("Attribute=" + String.valueOf(getAttribute()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Family justId(String id) {
    return new Family(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      Name,
      Age,
      Attribute);
  }
  public interface BuildStep {
    Family build();
    BuildStep id(String id);
    BuildStep name(String name);
    BuildStep age(Integer age);
    BuildStep attribute(String attribute);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String Name;
    private Integer Age;
    private String Attribute;
    @Override
     public Family build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Family(
          id,
          Name,
          Age,
          Attribute);
    }
    
    @Override
     public BuildStep name(String name) {
        this.Name = name;
        return this;
    }
    
    @Override
     public BuildStep age(Integer age) {
        this.Age = age;
        return this;
    }
    
    @Override
     public BuildStep attribute(String attribute) {
        this.Attribute = attribute;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, Integer age, String attribute) {
      super.id(id);
      super.name(name)
        .age(age)
        .attribute(attribute);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
    
    @Override
     public CopyOfBuilder attribute(String attribute) {
      return (CopyOfBuilder) super.attribute(attribute);
    }
  }
  
}
