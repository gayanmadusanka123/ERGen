<h1>${entity.name} Management</h1>

<h2>${entity.name} Form</h2>
<button
        type="button"
        (click)="cancel()">

    New ${entity.name}

</button>
<form>
    <#list entity.attributes as attribute>
        <#if !attribute.primaryKey>
    <label>${attribute.name}</label>

<#if attribute.type == "LocalDate">
    <input
        type="date"
        [(ngModel)]="${entity.name?lower_case}.${attribute.name?uncap_first}"
        name="${attribute.name?uncap_first}"
    >
<#elseif attribute.type == "Integer" || attribute.type == "Long">
    <input
        type="number"
        [(ngModel)]="${entity.name?lower_case}.${attribute.name?uncap_first}"
        name="${attribute.name?uncap_first}"
    >
<#else>
    <input
        type="text"
        [(ngModel)]="${entity.name?lower_case}.${attribute.name?uncap_first}"
        name="${attribute.name?uncap_first}"
    >
</#if>
        </#if>
    </#list>

    <#list entity.relationships as relationship>
        <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
    <label>${relationship.to}</label>

    <select
        [(ngModel)]="${entity.name?lower_case}.${relationship.to?uncap_first}"
        name="${relationship.to?uncap_first}"
    >
        <option [ngValue]="null">
            Select ${relationship.to}
        </option>

        <option
            *ngFor="let item of ${relationship.to?lower_case}s"
            [ngValue]="item">

            {{ item.name }}
        </option>
    </select>
        </#if>
    </#list>

    <button type="button" (click)="save()">Save</button>
    <button type="button" (click)="cancel()">Cancel</button>
</form>

<hr>

<table>
    <thead>
        <tr>
        <#list entity.attributes as attribute>
            <th>${attribute.name}</th>
        </#list>

        <#list entity.relationships as relationship>
            <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
            <th>${relationship.to}</th>
            </#if>
        </#list>
            <th>Actions</th>
        </tr>
        </thead>
    <tbody>
        @for (item of ${entity.name?lower_case}s; track item.id){
            <tr>
            <#list entity.attributes as attribute>
                <td>{{ item.${attribute.name?uncap_first} }}</td>
            </#list>
            <#list entity.relationships as relationship>
                <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
                <td>{{ item.${relationship.to?lower_case}.name }}</td>
                </#if>
            </#list>

                <td>
                    <button type="button" (click)="edit(item)">
                        Edit
                    </button>

                    <button type="button" (click)="delete(item.id)">
                        Delete
                    </button>
                </td>
            </tr>
        }
    </tbody>
</table>