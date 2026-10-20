<nav class="navbar">
    <div class="navbar-container">
        <a routerLink="/" class="navbar-brand">Dashboard</a>

        <ul class="navbar-menu">
        <#list pages as page>
            <li class="nav-item">
                <a routerLink="/${page.route}" routerLinkActive="active" class="nav-link">${page.route}</a>
            </li>
        </#list>
        </ul>

    </div>
</nav>