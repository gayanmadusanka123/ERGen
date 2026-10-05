<h2>${entity.name} Form</h2>

<form>
    <#list entity.attributes as attribute>
        <#if !attribute.primaryKey>
    <label>${attribute.name}</label>

    <input
        type="text"
        [(ngModel)]="${entity.name?lower_case}.${attribute.name}"
        name="${attribute.name}"
    >
        </#if>
    </#list>

    <#list entity.relationships as relationship>
        <#if relationship.type == "ManyToOne">
    <label>${relationship.to}</label>

    <select
        [(ngModel)]="${entity.name?lower_case}.${relationship.to?lower_case}"
        name="${relationship.to?lower_case}"
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

    <button type="button">Save</button>
</form>

<table>
    <thead>
        <tr>
        <#list entity.attributes as attribute>
            <th>${attribute.name}</th>
        </#list>

        <#list entity.relationships as relationship>
            <#if relationship.type == "ManyToOne">
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
                <td>{{ item.${attribute.name} }}</td>
            </#list>
            <#list entity.relationships as relationship>
                <#if relationship.type == "ManyToOne">
                <td>{{ item.${relationship.to?lower_case}.name }}</td>
                </#if>
            </#list>
            </tr>
        }
    </tbody>
</table>