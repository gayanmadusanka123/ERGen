<#list entity.relationships as relationship>
    <#if relationship.type == "ManyToOne">
import { ${relationship.to} } from './${relationship.to?uncap_first}';
    </#if>
</#list>

export interface ${entity.name}{
<#list entity.attributes as attribute>
    ${attribute.name}: ${attribute.typeScriptType};
</#list>

<#list entity.relationships as relationship>
    <#if relationship.type == "ManyToOne">
    ${relationship.to?uncap_first}: ${relationship.to};
    </#if>
</#list>
}