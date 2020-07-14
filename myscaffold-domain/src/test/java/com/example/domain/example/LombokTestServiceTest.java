package com.example.domain.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LombokTestServiceTest {

    @Test
    @DisplayName("001_equalsの検証: ")
    void test001() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isTrue();

    }

    @Test
    @DisplayName("002_equalsの検証: ")
    void test002() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.setField101("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("003_equalsの検証: ")
    void test003() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.setField102(999);

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("004_equalsの検証: ")
    void test004() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getList103().remove(0);
        entity2.getList103().add("11111");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("005_equalsの検証: ")
    void test005() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getList103().add("11111");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("006_equalsの検証: ")
    void test006() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getMap104().put("1", "b");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("007_equalsの検証: ")
    void test007() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getMap104().put("0", "a");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("008_equalsの検証: ")
    void test008() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildField105().setField201("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("009_equalsの検証: ")
    void test009() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildField105().getFieldList202().add("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("010_equalsの検証: ")
    void test010() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildList106().get(0).setField201("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("011_equalsの検証: ")
    void test011() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildList106().get(0).getFieldList202().add("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("012_equalsの検証: ")
    void test012() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildList106().get(0).setFieldList202(new ArrayList<>());
        entity2.getChildList106().get(0).getFieldList202().add("a2");
        entity2.getChildList106().get(0).getFieldList202().add("a1");
        entity2.getChildList106().get(0).getFieldList202().add("a3");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("013_equalsの検証: ")
    void test013() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.setField001("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("014_equalsの検証: ")
    void test014() {

        // 比較するエンティティを準備
        LombokCallSuperEntity entity1 = new LombokCallSuperEntity();
        setValue(entity1, "a");
        LombokCallSuperEntity entity2 = new LombokCallSuperEntity();
        setValue(entity2, "a");

        //
        entity2.setField001("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("015_builderの検証: ")
    void test015() {
        LombokBuilderEntity lombokBuilderEntity = create("aaa");
        System.out.println(lombokBuilderEntity.toString());
    }

    private void setValue(LombokChildEntity entity, String value) {
        entity.setField201(value);
        List<String> field202 = new ArrayList<>();
        field202.add(value + "1");
        field202.add(value + "2");
        field202.add(value + "3");
        entity.setFieldList202(field202);
    }

    private void setValue(LombokEntity entity, String value) {

        entity.setField001(value);
        entity.setField101(value);
        entity.setField102(1);
        entity.setList103(new ArrayList<>());
        entity.setMap104(new LinkedHashMap<>());
        entity.setChildField105(new LombokChildEntity());
        setValue(entity.getChildField105(), value);

        entity.setChildList106(new ArrayList<>());
        entity.getChildList106().add(new LombokChildEntity());

        entity.getList103().add(value + "1");
        entity.getMap104().put("1", value);
        setValue(entity.getChildList106().get(0), value);
    }

    private void setValue(LombokCallSuperEntity entity, String value) {

        entity.setField001(value);
        entity.setField101(value);
        entity.setField102(1);
        entity.setList103(new ArrayList<>());
        entity.setMap104(new LinkedHashMap<>());
        entity.setChildField105(new LombokChildEntity());
        setValue(entity.getChildField105(), value);

        entity.setChildList106(new ArrayList<>());
        entity.getChildList106().add(new LombokChildEntity());

        entity.getList103().add(value + "1");
        entity.getMap104().put("1", value);
        setValue(entity.getChildList106().get(0), value);
    }

    private LombokBuilderEntity create(String value) {
        return LombokBuilderEntity.builder()
                .firstName(value)
                .lastName(value)
                .age(1)
                .build();
    }

}