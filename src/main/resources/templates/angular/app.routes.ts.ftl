import { Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
<#list pages as page>
import { ${page.componentClass} } from './${page.route}/${page.componentFile}';
</#list>

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent,
    },
<#list pages as page>
    {
        path: '${page.route}',
        component: ${page.componentClass}
    }<#if page_has_next>,</#if>
</#list>

];