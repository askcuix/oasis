package io.askcuix.oasis.core.util;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

/**
 * Created by Chris on 16/1/8.
 */
public class ReflectionUtilTest {

    @Test
    public void getAndSetFieldValue() {
        TestBean bean = new TestBean();
        // 无需getter函数, 直接读取privateField
        assertEquals(ReflectionUtil.getFieldValue(bean, "privateField"), 1);
        // 绕过将publicField+1的getter函数,直接读取publicField的原始值
        assertEquals(ReflectionUtil.getFieldValue(bean, "publicField"), 1);

        bean = new TestBean();
        // 无需setter函数, 直接设置privateField
        ReflectionUtil.setFieldValue(bean, "privateField", 2);
        assertEquals(bean.inspectPrivateField(), 2);

        // 绕过将publicField+1的setter函数,直接设置publicField的原始值
        ReflectionUtil.setFieldValue(bean, "publicField", 2);
        assertEquals(bean.inspectPublicField(), 2);

        try {
            ReflectionUtil.getFieldValue(bean, "notExist");
            fail("Expect exception");
        } catch (IllegalArgumentException e) {

        }

        try {
            ReflectionUtil.setFieldValue(bean, "notExist", 2);
            fail("Expect exception");
        } catch (IllegalArgumentException e) {

        }

    }

    @Test
    public void invokeGetterAndSetter() {
        TestBean bean = new TestBean();
        assertEquals(ReflectionUtil.invokeGetter(bean, "publicField"), bean.inspectPublicField() + 1);

        bean = new TestBean();
        // 通过setter的函数将+1
        ReflectionUtil.invokeSetter(bean, "publicField", 10);
        assertEquals(bean.inspectPublicField(), 10 + 1);
    }

    @Test
    public void invokeMethod() {
        TestBean bean = new TestBean();
        // 使用函数名+参数类型的匹配
        assertEquals(ReflectionUtil
                .invokeMethod(bean, "privateMethod", new Class[]{String.class}, new Object[]{"Chris"}), "hello Chris");

        // 仅匹配函数名
        assertEquals(ReflectionUtil.invokeMethodByName(bean, "privateMethod", new Object[]{"Chris"}), "hello Chris");

        // 函数名错
        try {
            ReflectionUtil.invokeMethod(bean, "notExistMethod", new Class[]{String.class}, new Object[]{"Chris"});
            fail("Expect exception");
        } catch (IllegalArgumentException e) {

        }

        // 参数类型错
        try {
            ReflectionUtil.invokeMethod(bean, "privateMethod", new Class[]{Integer.class}, new Object[]{"Chris"});
            fail("Expect exception");
        } catch (RuntimeException e) {

        }

        // 函数名错
        try {
            ReflectionUtil.invokeMethodByName(bean, "notExistMethod", new Object[]{"Chris"});
            fail("Expect exception");
        } catch (IllegalArgumentException e) {

        }

    }

    @Test
    public void getSuperClassGenricType() {
        // 获取第1，2个泛型类型
        assertEquals(ReflectionUtil.getClassGenricType(TestBean.class), String.class);
        assertEquals(ReflectionUtil.getClassGenricType(TestBean.class, 1), Long.class);

        // 定义父类时无泛型定义
        assertEquals(ReflectionUtil.getClassGenricType(TestBean2.class), Object.class);

        // 无父类定义
        assertEquals(ReflectionUtil.getClassGenricType(TestBean3.class), Object.class);
    }

    @Test
    public void convertReflectionExceptionToUnchecked() {
        IllegalArgumentException iae = new IllegalArgumentException();
        // ReflectionException,normal
        RuntimeException e = ReflectionUtil.convertReflectionExceptionToUnchecked(iae);
        assertEquals(e.getCause(), iae);

        // InvocationTargetException,extract it's target exception.
        Exception ex = new Exception();
        e = ReflectionUtil.convertReflectionExceptionToUnchecked(new InvocationTargetException(ex));
        assertEquals(e.getCause(), ex);

        // UncheckedException, ignore it.
        RuntimeException re = new RuntimeException("abc");
        e = ReflectionUtil.convertReflectionExceptionToUnchecked(re);
        assertEquals(e.getMessage(), "abc");

        // Unexcepted Checked exception.
        e = ReflectionUtil.convertReflectionExceptionToUnchecked(ex);
        assertEquals(e.getMessage(), "Unexpected Checked Exception.");
    }

    public static class ParentBean<T, ID> {
    }

    public static class TestBean extends ParentBean<String, Long> {
        /**
         * 没有getter/setter的field
         */
        private int privateField = 1;
        /**
         * 有getter/setter的field
         */
        private int publicField = 1;

        // 通过getter函数会将属性值+1
        public int getPublicField() {
            return publicField + 1;
        }

        // 通过setter函数会将输入值加1
        public void setPublicField(int publicField) {
            this.publicField = publicField + 1;
        }

        public int inspectPrivateField() {
            return privateField;
        }

        public int inspectPublicField() {
            return publicField;
        }

        private String privateMethod(String text) {
            return "hello " + text;
        }
    }

    public static class TestBean2 extends ParentBean {
    }

    public static class TestBean3 {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
