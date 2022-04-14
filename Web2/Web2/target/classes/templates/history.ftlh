<#import "parts/common.ftlh" as c>

<@c.page "Simple Tests">
    <div class="row">
        <div class="col-lg-6 offset-3 text-center">
            <h1 style="margin-top: 25px; margin-bottom: 25px">История задолженностей</h1>
            <table class="table table-bordered table-hover align-middle">
                <thead>
                <tr>
                    <th>Тип</th>
                    <th>Сумма</th>
                    <th>Статус</th>
                </tr>
                </thead>
                <tbody>
                <#list bills as bill>
                <tr>
                    <td>${bill.type}</td>
                    <td>${bill.value}</td>
                    <td>${bill.status}</td>
                </tr>
                <#else>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</@c.page>