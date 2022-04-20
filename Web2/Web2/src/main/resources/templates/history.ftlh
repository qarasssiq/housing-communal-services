<#import "parts/common.ftlh" as c>

<@c.page "История">
    <div class="row">
        <div class="col-lg-6 offset-3 text-center">
            <h1 style="margin-top: 25px; margin-bottom: 25px">История платежей</h1>
            <table class="table table-bordered table-hover align-middle">
                <thead>
                <tr>
                    <th>Счёт</th>
                    <th>Дата</th>
                    <th>Сумма</th>
                </tr>
                </thead>
                <tbody>
                <#list payments as payment>
                <tr>
                    <td>${payment.billId.id}</td>
                    <td>${payment.date.format(formatter)}</td>
                    <td>₽ ${payment.amount}</td>
                </tr>
                <#else>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <div class="text-center">
        <input type="button" value="Назад к счетам услуги" onClick='location.href="/address/${addressId}/service/${serviceId}"'>
    </div>
</@c.page>