package com.ergen.generator;

import com.ergen.model.Entity;
import com.ergen.model.Relationship;
import com.ergen.ui.UIPage;
import freemarker.template.Configuration;
import freemarker.template.Template;

import com.ergen.ui.UISpecification;

import java.util.*;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class AngularGenerator {
    private final Configuration configuration;

    private String getFeatureName(Entity entity) {
        return entity.getName().toLowerCase() + "s";
    }

    public AngularGenerator() throws Exception{
        configuration = new Configuration(Configuration.VERSION_2_3_34);

        configuration.setClassForTemplateLoading(AngularGenerator.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    public void generate(List<Entity> entities) throws Exception{
        UISpecification specification = new UISpecification();

        List<UIPage> pages = new ArrayList<>();

        for(Entity entity : entities){
            UIPage page = new UIPage();

            page.setName(entity.getName() + " Management");
            page.setEntity(entity.getName());
            page.setRoute(entity.getName().toLowerCase() + "s");
            page.setView("table");

            pages.add(page);
        }

        specification.setPages(pages);

        generate(entities, specification);
    }

    public void generate(List<Entity> entities, UISpecification specification) throws Exception{
        if(specification == null || specification.getPages() == null){
            throw new IllegalArgumentException(
                    "UI specification or its pages are missing."
            );
        }

        generateModels(entities);
        generateServices(entities);
        generateRootTemplate();

        List<UIPage> supportedPages = new ArrayList<>();
        Set<String> usedRoutes = new HashSet<>();

        for(UIPage page : specification.getPages()){
            if(page.getEntity() == null || page.getEntity().isBlank()){
                System.out.println(
                        "Skipping page '" + page.getName() + "': pages without an entity are not supported yet."
                );
                continue;
            }

            String requestedView = page.getView();

            if(requestedView == null || requestedView.isBlank()){
                requestedView = "crud";
            }

            if(requestedView.equalsIgnoreCase("crud") || requestedView.equalsIgnoreCase("table")){
                page.setView("table");
            }else{
                System.out.println(
                        "Skipping page '" + page.getName()
                        + "': view '" + requestedView
                        + "' is not supported by the current templates."
                );
                continue;
            }

            System.out.println(
                    "AI requested entity: [" + page.getEntity()
                            + "], length: " + page.getEntity().length()
            );

            System.out.println("Entities from XML:");
            for (Entity entity : entities) {
                System.out.println(" - [" + entity.getName() + "]");
            }


            Entity matchedEntity = null;

            String requestedEntity = page.getEntity();

            System.out.println("AI requested entity: [" + requestedEntity + "]");

            for (Entity entity : entities) {
                String xmlEntityName = entity.getName();

                System.out.println(
                        "Comparing [" + requestedEntity + "] with ["
                                + xmlEntityName + "]"
                );

                if (xmlEntityName != null
                        && requestedEntity != null
                        && xmlEntityName.trim().equalsIgnoreCase(
                        requestedEntity.trim())) {

                    matchedEntity = entity;

                    System.out.println(
                            "MATCH FOUND: " + matchedEntity.getName()
                    );

                    break;
                }
            }

            if (matchedEntity == null) {
                throw new IllegalArgumentException(
                        "AI specification references unknown entity: ["
                                + requestedEntity + "]"
                );
            }

            String entityName = matchedEntity.getName();
            String entityLower = entityName.toLowerCase();

            page.setEntity(entityName);
            page.setRoute(entityLower + "s");
            page.setView("table");

            page.setComponentClass(entityName + "sComponent");
            page.setComponentFile(entityLower + "s.component");

            page.setRelationships(matchedEntity.getRelationships());

            generateTablePage(page, matchedEntity);
            generateComponents(page, matchedEntity);
            supportedPages.add(page);

        }
        generateHomePage(supportedPages);
        generateHomeComponent();

        System.out.println("Supported pages count: " + supportedPages.size());
        System.out.println("Starting route generating...");
        generateRoutes(supportedPages);
        System.out.println("Finished route generating");
    }

    public void generateModels(List<Entity> entities) throws Exception{

        Template template = configuration.getTemplate("angular/model.ts.ftl");

        for (Entity entity : entities){
            File outputDirectory = new File("output/frontend/src/app/" + getFeatureName(entity) + "/models");

            outputDirectory.mkdirs();

            Map<String, Object> data = new HashMap<>();
            data.put("entity", entity);

            File outputFile = new File(outputDirectory, entity.getName().toLowerCase() + ".model.ts");

            try(Writer writer = new FileWriter(outputFile)){
                template.process(data, writer);
            }

            System.out.println("Generated: " + outputFile.getPath());

        }
    }

    public void generateServices(List<Entity> entities) throws Exception {

        Template template = configuration.getTemplate("angular/service.ts.ftl");

        for (Entity entity : entities) {
            File outputDirectory = new File("output/frontend/src/app/" + getFeatureName(entity) + "/services");

            outputDirectory.mkdirs();

            Map<String, Object> data = new HashMap<>();
            data.put("entity", entity);

            File outputFile = new File(outputDirectory, entity.getName().toLowerCase() + ".service.ts");

            try(Writer writer = new FileWriter(outputFile)){
                template.process(data, writer);
            }

            System.out.println("Generated: " + outputFile.getPath());
        }
    }

    private void generateTablePage(UIPage page, Entity entity) throws Exception{
        Template template = configuration.getTemplate("angular/pages/table.component.html.ftl");

        Map<String, Object> data = new HashMap<>();
        data.put("entity", entity);
        data.put("page", page);

        File outputDirectory = new File("output/frontend/src/app/" + getFeatureName(entity));

        outputDirectory.mkdirs();

        File outputFile = new File(outputDirectory, getFeatureName(entity) + ".component.html");

        try(Writer writer = new FileWriter(outputFile)){
            template.process(data, writer);
        }

        System.out.println("Generated: " + outputFile.getPath());
    }

    private void generateComponents(UIPage page, Entity entity) throws Exception{
        Template template = configuration.getTemplate("angular/components/table.component.ts.ftl");

        Map<String, Object> data = new HashMap<>();
        data.put("entity", entity);
        data.put("page", page);

        File outputDirectory = new File("output/frontend/src/app/" + getFeatureName(entity));

        outputDirectory.mkdirs();
        File outputFile = new File(outputDirectory,getFeatureName(entity) + ".component.ts");

        try(Writer writer = new FileWriter(outputFile)){
            template.process(data, writer);
        }

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

    private void generateHomePage(List<UIPage> pages) throws Exception{
        Template template = configuration.getTemplate("angular/pages/home.component.html.ftl");

        File outputDirectory = new File("output/frontend/src/app/home/");
        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }

        Map<String, Object> data = new HashMap<>();

        data.put("pages", pages);

        File outputFile = new File(outputDirectory,"home.component.html");
        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

    private void generateHomeComponent() throws Exception{
        Template template = configuration.getTemplate("angular/components/home.component.ts.ftl");

        File outputDirectory = new File("output/frontend/src/app/home/");
        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }

        Map<String, Object> data = new HashMap<>();

        File outputFile = new File(outputDirectory,"home.component.ts");
        Writer writer = new FileWriter(outputFile);

        template.process(data, writer);
        writer.close();

        System.out.println("Generated: " + outputFile.getPath());
    }

}
