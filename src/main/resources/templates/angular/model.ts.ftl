export interface ${entity.name}{
<#list entity.attributes as attribute>
    ${attribute.name}: ${attribute.typeScriptType};
</#list>
}