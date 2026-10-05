package com.generated.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import java.util.List;

@Entity
@Table(name = "${entity.name?lower_case}")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ${entity.name}{

<#list entity.attributes as attribute>
<#if attribute.primaryKey>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
</#if>
<#if attribute.required>
    @Column(nullable = false)
<#else>
    @Column(nullable = true)
</#if>
    private ${attribute.type} ${attribute.name};
</#list>

<#list entity.relationships as relationship>
    <#if relationship.from == entity.name>
        <#if relationship.type == "ManyToOne">
    @ManyToOne
    @JoinColumn(name = "${relationship.fieldName}_id")
    private ${relationship.to} ${relationship.fieldName};
        </#if>
        <#if relationship.type == "OneToOne">
    @OneToOne
    @JoinColumn(name = "${relationship.fieldName}_id")
    private ${relationship.to} ${relationship.fieldName};
        </#if>
        <#if relationship.type == "ManyToMany">
    @ManyToMany
        @JoinTable(
        name = "${relationship.from?lower_case}_${relationship.to?lower_case}",
        joinColumns = @JoinColumn(name = "${relationship.from?lower_case}_id"),
        inverseJoinColumns = @JoinColumn(name = "${relationship.to?lower_case}_id")
    )
    private List<${relationship.to}> ${relationship.fieldName};
        </#if>
    <#elseif relationship.to == entity.name>
        <#if relationship.type == "ManyToOne">
    @OneToMany(mappedBy = "${relationship.fieldName}")
    private List<${relationship.from}> ${relationship.inverseFieldName};
        </#if>
        <#if relationship.type == "OneToOne">
    @OneToOne(mappedBy = "${relationship.fieldName}")
    private ${relationship.from} ${relationship.inverseFieldName};
        </#if>
        <#if relationship.type == "ManyToMany">
    @ManyToMany(mappedBy = "${relationship.fieldName}")
    private List<${relationship.from}> ${relationship.inverseFieldName};
        </#if>
    </#if>
</#list>
}
