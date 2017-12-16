"# spellrefiner-gfdtk" 


Requirements:
Wildfly server v.11
Java 8
MySQL server v. 5.7.19


Create database in mysql.
Use ddl script from file: spellrefiner\ddl\mysql_ddl.txt 

JVM settings "SPELLREFINER_FIXED_SALT" is required for encrypting passwords (Set it to random string 20 bytes length, for example: SPELLREFINER_FIXED_SALT=8367616e6ca2f9d342e75e8437ad9c6ff55ca08)
In hosting "https://app.jelastic.regruhosting.ru" it could be done by adding text in file.
file: /opt/shared/configuration/variables.conf 
text: -DSPELLREFINER_FIXED_SALT=8367616e6ca2f9d342e75e8437ad9c6ff55ca08 


Install mysql driver for Wildfly.
Upload files to:
${WILDFLY_HOME}/modules/system/layers/base/com/mysql/driver/main/mysql-connector-java-5.1.35-bin.jar
${WILDFLY_HOME}/modules/system/layers/base/com/mysql/driver/main/module.xml

File module.xml:
<module xmlns="urn:jboss:module:1.3" name="com.mysql.driver">
 <resources>
  <resource-root path="mysql-connector-java-5.1.35-bin.jar" />
 </resources>
 <dependencies>
  <module name="javax.api"/>
  <module name="javax.transaction.api"/>
 </dependencies>
</module> 


Config datasource in Wildfly.
Edit file standalone.xml - add text:

In <drivers> section:

                    <driver name="mysql" module="com.mysql.driver ">
                        <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
                    </driver>

In <datasources> section:

             <datasource jndi-name="java:jboss/datasources/spellrefiner" pool-name="spellrefiner" enabled="true" use-java-context="true" use-ccm="true">
                    <connection-url>jdbc:mysql://node145015-spellrefiner-gfdtk.jelastic.regruhosting.ru:3306/spellrefiner?useUnicode=yes&amp;characterEncoding=UTF-8&amp;useSSL=false</connection-url>
                    <driver>mysql</driver>
                    <pool>
                        <flush-strategy>IdleConnections</flush-strategy>
                    </pool>
                    <security>
                        <user-name>root</user-name>
                        <password>DNTbbl14466</password>
                    </security>
                    <validation>
                        <check-valid-connection-sql>SELECT 1</check-valid-connection-sql>
                        <background-validation>true</background-validation>
                        <background-validation-millis>60000</background-validation-millis>
                    </validation>
                </datasource>

Change values "connection-url", "user-name", "password" 

