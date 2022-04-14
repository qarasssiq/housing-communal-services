<#import "parts/common.ftlh" as c>

<@c.page "Адрес">
    <div class="row g-0">
        <div class="col-lg-6 offset-3 text-center">
            <h1 style="margin-top: 25px; margin-bottom: 25px">Услуги</h1>
            <table class="table table-bordered table-hover align-middle">
                <thead>
                <tr>
                    <th>Тип</th>
                    <th>Лицевой счет</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list services as service>
                    <tr>
                        <td>${service.type}</td>
                        <td>${service.personalAccountNumber}</td>
                        <td><input type="button" value="Счета" onClick='location.href="/address/${service.addressId.id}/service/${service.id}"'></td>
                    </tr>
                <#else>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <div class="text-center">
        <input type="button" value="Добавить новую услугу" onClick='location.href="/address/${address.id}/createService"'>
        <input type="button" value="Назад к адресам" onClick='location.href="/main"'>
    </div>
</@c.page>