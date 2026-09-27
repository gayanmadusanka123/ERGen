<table>
    <thead>
        <tr>
        <#list entity.attributes as attribute>
            <th>${attribute.name}</th>
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
            </tr>
        }
    </tbody>
</table>