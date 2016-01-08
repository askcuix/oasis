package io.askcuix.oasis.core.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Chris on 16/1/8.
 */
public class JsonMapperTest {
    private static JsonMapper mapper = JsonMapper.nonDefaultMapper();

    /**
     * 序列化对象/集合到Json字符串.
     */
    @Test
    public void toJson() throws Exception {
        // Bean
        TestBean bean = new TestBean("A");
        String beanString = mapper.toJson(bean);
        System.out.println("Bean:" + beanString);
        assertEquals(beanString, "{\"name\":\"A\"}");

        // Map
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "A");
        map.put("age", 2);
        String mapString = mapper.toJson(map);
        System.out.println("Map:" + mapString);
        assertEquals(mapString, "{\"name\":\"A\",\"age\":2}");

        // List<String>
        List<String> stringList = Lists.newArrayList("A", "B", "C");
        String listString = mapper.toJson(stringList);
        System.out.println("String List:" + listString);
        assertEquals(listString, "[\"A\",\"B\",\"C\"]");

        // List<Bean>
        List<TestBean> beanList = Lists.newArrayList(new TestBean("A"), new TestBean("B"));
        String beanListString = mapper.toJson(beanList);
        System.out.println("Bean List:" + beanListString);
        assertEquals(beanListString, "[{\"name\":\"A\"},{\"name\":\"B\"}]");

        // Bean[]
        TestBean[] beanArray = new TestBean[]{new TestBean("A"), new TestBean("B")};
        String beanArrayString = mapper.toJson(beanArray);
        System.out.println("Array List:" + beanArrayString);
        assertEquals(beanArrayString, "[{\"name\":\"A\"},{\"name\":\"B\"}]");
    }

    /**
     * 从Json字符串反序列化对象/集合.
     */
    @Test
    public void fromJson() throws Exception {
        // Bean
        String beanString = "{\"name\":\"A\"}";
        TestBean bean = mapper.fromJson(beanString, TestBean.class);
        System.out.println("Bean:" + bean);

        // Map
        String mapString = "{\"name\":\"A\",\"age\":2}";
        Map<String, Object> map = mapper.fromJson(mapString, HashMap.class);
        System.out.println("Map:");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        // List<String>
        String listString = "[\"A\",\"B\",\"C\"]";
        List<String> stringList = mapper.getMapper().readValue(listString, List.class);
        System.out.println("String List:");
        for (String element : stringList) {
            System.out.println(element);
        }

        // List<Bean>
        String beanListString = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
        List<TestBean> beanList = mapper.getMapper().readValue(beanListString, mapper.contructCollectionType(List.class, TestBean.class));
        System.out.println("Bean List:");
        for (TestBean element : beanList) {
            System.out.println(element);
        }
    }

    /**
     * 测试传入空对象,空字符串,Empty的集合,"null"字符串的结果.
     */
    @Test
    public void nullAndEmpty() {
        // toJson测试 //

        // Null Bean
        TestBean nullBean = null;
        String nullBeanString = mapper.toJson(nullBean);
        assertEquals(nullBeanString, "null");

        // Empty List
        List<String> emptyList = Lists.newArrayList();
        String emptyListString = mapper.toJson(emptyList);
        assertEquals(emptyListString, "[]");

        // fromJson测试 //

        // Null String for Bean
        TestBean nullBeanResult = mapper.fromJson(null, TestBean.class);
        assertNull(nullBeanResult);

        nullBeanResult = mapper.fromJson("null", TestBean.class);
        assertNull(nullBeanResult);

        // Null/Empty String for List
        List nullListResult = mapper.fromJson(null, List.class);
        assertNull(nullListResult);

        nullListResult = mapper.fromJson("null", List.class);
        assertNull(nullListResult);

        nullListResult = mapper.fromJson("[]", List.class);
        assertTrue(nullListResult.isEmpty());
    }

    /**
     * 测试三种不同的输出风格.
     */
    @Test
    public void threeOutputStyle() {
        // 打印全部属性
        JsonMapper normalMapper = new JsonMapper();
        TestBean bean = new TestBean("A");
        assertEquals(normalMapper.toJson(bean), "{\"name\":\"A\",\"defaultValue\":\"hello\",\"nullValue\":null}");

        // 不打印nullValue属性
        JsonMapper nonNullMapper = JsonMapper.nonEmptyMapper();
        assertEquals(nonNullMapper.toJson(bean), "{\"name\":\"A\",\"defaultValue\":\"hello\"}");

        // 不打印默认值未改变的nullValue与defaultValue属性
        JsonMapper nonDefaultMapper = JsonMapper.nonDefaultMapper();
        assertEquals(nonDefaultMapper.toJson(bean), "{\"name\":\"A\"}");
    }

    public static class TestBean {

        private String name;
        private String defaultValue = "hello";
        private String nullValue = null;

        public TestBean() {
        }

        public TestBean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getNullValue() {
            return nullValue;
        }

        public void setNullValue(String nullValue) {
            this.nullValue = nullValue;
        }

        @Override
        public String toString() {
            return "TestBean [defaultValue=" + defaultValue + ", name=" + name + ", nullValue=" + nullValue + "]";
        }
    }
}
