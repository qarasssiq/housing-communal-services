<#import "parts/common.ftlh" as c>

<@c.page "Администратор">
    <div class="row g-0">
        <div class="col-lg-6 offset-3 text-center">
            <h1 style="margin-top: 25px; margin-bottom: 25px">Пользователи</h1>
            <table class="table table-bordered table-hover align-middle">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Имя</th>
                    <th>Роль</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list users as user>
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.username}</td>
                        <td>${user.userRole}</td>
                        <td><input type="button" class="deleteButton" value="Забанить" onClick='location.href="/admin"'></td>
                    </tr>
                <#else>
                    Пусто
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</@c.page>