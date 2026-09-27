package com.ergen.generator;

import com.ergen.model.Entity;
import com.ergen.ui.UIPage;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AngularGenerator {
    private final Configuration configuration;

    public AngularGenerator() throws Exception{
        configuration = new Configuration(Configuration.VERSION_2_3_34);

        configuration.setClassForTemplateLoading(AngularGenerator.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    public void generate(List<Entity> entities) throws Exception{
        generateModels(entities);
        generateServices(entities);

        generateRootTemplate();

        List<UIPage> pages = new ArrayList<>();

        for(Entity entity : entities){
            UIPage page = new UIPage();

            page.setName(entity.getName() + " Management");
            page.setEntity(entity.getName());
            page.setRoute(entity.getName().toLowerCase() + "s");
            page.setView("table");

            page.setComponentClass(entity.getName() + "sComponent");
            page.setComponentFile(entity.getName().toLowerCase() + "s.component");

            pages.add(page);

            generateTablePage(page, entity);
            generateComponents(page, entity);
        }
        generateRoutes(pages);
    }

    public void generateModels(List<Entity> entities) throws Exception{
        File outputDirectory = new File("output/frontend/src/app/models");

        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }

        Template template = configuration.getTemplate("angular/model.ts.ftl");

        for (Entity entity : entities){
            Map<String, Object> data = new HashMap<>();
            data.put("entity", entity);

            File outputFile = new File(outputDirectory, entity.getName().toLowerCase() + ".ts");

            Writer writer = new FileWriter(outputFile);

            template.process(data, writer);
            writer.close();

            System.out.println("Generated: " + outputFile.getPath());
        }
    }

    public void generateServices(List<Entity> entities) throws Exception {
        File outputDirectory = new File("output/frontend/src/app/services");

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        Template template = configuration.getTemplate("angular/service.ts.ftl");

        for (Entity entity : entities) {
            Map<String, Object> data = new HashMap<>();
            data.put("entity", entity);

            File outputFile = new File(outputDirectory, entity.getName().toLowerCase() + ".service.ts");

            Writer writer = new FileWriter(outputFile);

            template.process(data, writer);
            writer.close();

            System.out.println("Generated: " + outputFile.getPath());
        }
    }

    private void generateTablePage(UIPage page, Entity entity) throws Exception{
        Template template = configuration.getTemplate("angular/pages/table.component.html.ftl");

        Map<String, Object> data = new HashMap<>();

        data.put("entity", entity);
        data.put("page", page);

        File outputDirectory = new File("output/frontend/src/app/features/pages");

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        File outputFile = new File(outputDirectory, entity.getName().toLowerCase() + "s.component.html");

        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

    private void generateComponents(UIPage page, Entity entity) throws Exception{
        Template template = configuration.getTemplate("angular/components/table.component.ts.ftl");

        Map<String, Object> data = new HashMap<>();

        data.put("entity", entity);
        data.put("page", page);


        File outputDirectory = new File("output/frontend/src/app/features/components");

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        File outputFile = new File(outputDirectory,entity.getName().toLowerCase() + "s.component.ts");

        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

    private void generateRoutes(List<UIPage> pages) throws Exception{
        Template template = configuration.getTemplate("angular/app.routes.ts.ftl");

        Map<String, Object> data = new HashMap<>();

        data.put("pages", pages);

        File outputDirectory = new File("output/frontend/src/app");

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        File outputFile = new File(outputDirectory,"app.routes.ts");

        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

    private void generateRootTemplate() throws Exception{
        Template template = configuration.getTemplate("angular/app.html.ftl");

        File outputDirectory = new File("output/frontend/src/app");
        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }

        Map<String, Object> data = new HashMap<>();

        File outputFile = new File(outputDirectory,"app.html");
        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

}
