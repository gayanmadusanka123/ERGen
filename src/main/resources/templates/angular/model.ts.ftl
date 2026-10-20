<#list entity.relationships as relationship>
    <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
import { ${relationship.to} } from '../../${relationship.to?uncap_first}s/models/${relationship.to?uncap_first}.model';
    </#if>
</#list>

export interface ${entity.name}{
<#list entity.attributes as attribute>
    ${attribute.name?uncap_first}: ${attribute.typeScriptType};
</#list>

<#list entity.relationships as relationship>
    <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
    ${relationship.to?uncap_first}: ${relationship.to};
    </#if>
</#list>
}