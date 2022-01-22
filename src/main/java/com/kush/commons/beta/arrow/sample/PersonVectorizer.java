package com.kush.commons.beta.arrow.sample;

import static org.apache.arrow.vector.types.pojo.FieldType.nullable;

import java.util.List;

import org.apache.arrow.vector.UInt1Vector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.complex.StructVector;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;

import com.kush.commons.beta.arrow.Vectorizer;

public class PersonVectorizer implements Vectorizer<Person> {

    private final Schema personSchema;

    public PersonVectorizer() {
        personSchema = createSchema();
    }

    @Override
    public Schema getSchema() {
        return personSchema;
    }

    @Override
    public void vectorize(int index, Person person, VectorSchemaRoot schemaRoot) {
        // Using setSafe: it increases the buffer capacity if needed
        var personVector = (StructVector) schemaRoot.getVector("person");
        ((VarCharVector) personVector.getChild("name")).setSafe(index, person.name.getBytes());
        ((UInt1Vector) personVector.getChild("age")).setSafe(index, person.age);

        var cityVector = (StructVector) personVector.getChild("city");
        ((VarCharVector) cityVector.getChild("cityName")).setSafe(index, person.city.cityName.getBytes());
        ((VarCharVector) cityVector.getChild("stateName")).setSafe(index, person.city.stateName.getBytes());
    }

    private Schema createSchema() {
        var cityName = new Field("cityName", nullable(new ArrowType.Utf8()), null);
        var stateName = new Field("stateName", nullable(new ArrowType.Utf8()), null);
        var city = new Field("city", nullable(new ArrowType.Struct()), List.of(cityName, stateName));

        var name = new Field("name", nullable(new ArrowType.Utf8()), null);
        var age = new Field("age", nullable(new ArrowType.Int(8, false)), null);
        var person = new Field("person", nullable(new ArrowType.Struct()), List.of(name, age, city));

        return new Schema(List.of(person));
    }
}
