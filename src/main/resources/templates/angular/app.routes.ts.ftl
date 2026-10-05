import { Routes } from '@angular/router';

<#list pages as page>
import { ${page.componentClass} } from './features/components/${page.componentFile}';
</#list>

export const routes: Routes = [

<#list pages as page>
    {
        path: '${page.route}',
        component: ${page.componentClass}
    }<#if page_has_next>,</#if>
</#list>

];