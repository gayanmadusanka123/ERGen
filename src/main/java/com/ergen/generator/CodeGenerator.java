package com.ergen.generator;

import com.ergen.database.Postgres;
import com.ergen.model.Entity;
import com.ergen.parser.XmlParser;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {
    private final Configuration configuration;

    public CodeGenerator() throws Exception{
        configuration = new Configuration(Configuration.VERSION_2_3_34);

        configuration.setClassForTemplateLoading(CodeGenerator.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    public void generateEntities(List<Entity> entities) throws Exception{
        File outputDirectory = new File("output/src/main/java/com/generated/entity");

        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }

        Template template = configuration.getTemplate("Entity.java.ftl");

        for (Entity entity : entities){
            Map<String, Object> data = new HashMap<>();
            data.put("entity", entity);

            File outputFile = new File(outputDirectory, entity.getName() + ".java");

            Writer writer = new FileWriter(outputFile);

            template.process(data, writer);
            writer.close();

            System.out.println("Generated: " + outputFile.getPath());
        }
    }

    public void generateRepositeries(List<Entity> entities) throws Exception{
        File outputDirectory = new File("output/src/main/java/com/generated/repository");

        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }
        Template template = configuration.getTemplate("Repository.java.ftl");
        for (Entity entity : entities){
            Map<String, Object> data = new HashMap<>();
            data.put("entity", entity);

            File outputFile = new File(outputDirectory, entity.getName() + "Repository.java");

            Writer writer = new FileWriter(outputFile);

            template.process(data, writer);
            writer.close();

            System.out.println("Generated: " + outputFile.getPath());
        }
    }
    public void generateServices(List<Entity> entities) throws Exception{
        File outputDirectory = new File("output/src/main/java/com/generated/service");

        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }
        Template template = configuration.getTemplate("Service.java.ftl");
        for (Entity entity : entities){
            Map<String, Object> data = new HashMap<>();
            data.put("entity", entity);
            data.put("primaryKey", entity.getPrimaryKey());

            File outputFile = new File(outputDirectory, entity.getName() + "Service.java");

            Writer writer = new FileWriter(outputFile);

            template.process(data, writer);
            writer.close();

            System.out.println("Generated: " + outputFile.getPath());
        }
    }

    public void generateControllers(List<Entity> entities) throws Exception{
        File outputDirectory = new File("output/src/main/java/com/generated/controller");

        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }
        Template template = configuration.getTemplate("Controller.java.ftl");
        for (Entity entity : entities){
            Map<String, Object> data = new HashMap<>();
            data.put("entity", entity);
            data.put("primaryKey", entity.getPrimaryKey());

            File outputFile = new File(outputDirectory, entity.getName() + "Controller.java");

            Writer writer = new FileWriter(outputFile);

            template.process(data, writer);
            writer.close();

            System.out.println("Generated: " + outputFile.getPath());
        }
    }

    public void generateMainClass() throws Exception{
        File outputDirectory = new File("output/src/main/java/com/generated");

        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }
        Template template = configuration.getTemplate("Main.java.ftl");

        Map<String, Object> data = new HashMap<>();

        File outputFile = new File(outputDirectory, "GeneratedApplication.java");
        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

    public void generatePOMXML() throws Exception{
        File outputDirectory = new File("output");

        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }
        Template template = configuration.getTemplate("pom.xml.ftl");

        Map<String, Object> data = new HashMap<>();

        File outputFile = new File(outputDirectory, "pom.xml");
        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

    public void generateApplicationProperties(XmlParser parser) throws Exception{
        File outputDirectory = new File("output/src/main/resources");

        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }
        Template template = configuration.getTemplate("application.properties.ftl");

        Map<String, Object> data = new HashMap<>();

        List<Postgres> databases = parser.getDatabase();

        if (databases.isEmpty()) {
            throw new RuntimeException("No database configuration found in XML");
        }

        Postgres database = databases.getFirst();

        data.put("database", database);

        File outputFile = new File(outputDirectory, "application.properties");
        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

}
