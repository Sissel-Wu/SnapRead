<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<!-- Generated 2016-2-25 21:24:07 by Hibernate Tools 3.4.0.CR1 -->
<hibernate-mapping>
    <class name="po.ArticlePO" table="ARTICLEPO">
        <id name="post_id" type="java.lang.String">
            <column name="post_id" />
            <generator class="assigned" />
        </id>
        <property name="user_id" type="java.lang.String">
            <column name="user_id" />
        </property>
        <property name="title" type="java.util.Calendar">
            <column name="title" />
        </property>
        <property name="content" type="java.lang.String">
            <column name="content" />
        </property>
        <property name="type" type="java.lang.String">
            <column name="type" />
        </property>
        <property name="post_url" type="java.lang.String">
            <column name="post_url" />
        </property>
        <property name="post_img" type="java.lang.String">
            <column name="feature" />
        </property>
        <property name="feature" type="java.lang.String">
            <column name="feature" />
        </property>
    </class>
</hibernate-mapping>