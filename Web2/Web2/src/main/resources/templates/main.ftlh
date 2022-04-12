<#import "parts/common.ftlh" as c>

<@c.page "Simple Tests">
    <div class="row">
        <div class="col-lg-6 offset-3 text-center">
            <h1 style="margin-top: 25px; margin-bottom: 25px">Список ваших адресов</h1>
            <table class="table table-bordered table-hover align-middle">
                <thead>
                <tr>
                    <th>Населённый пункт</th>
                    <th>Адрес</th>
                </tr>
                </thead>
                <tbody>
                <#list addresses as address>
                    <tr>
                        <td>${address.city}</td>
                        <td>${address.address}</td>
                        <td><input type="button" value="Услуги" onClick='location.href="/address/${address.id}"'></td>
                    </tr>
                <#else>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <div class="text-center">
        <input type="button" value="Добавить новый адрес" onClick='location.href="/address/create"'>
    </div>
</@c.page>