<#import "parts/common.ftlh" as c>

<@c.page "Услуга">
    <div class="row">
        <div class="col-lg-6 offset-3 text-center">
            <h1 style="margin-top: 25px; margin-bottom: 25px">Счета</h1>
            <table class="table table-bordered table-hover align-middle">
                <thead>
                <tr>
                    <th>Дата</th>
                    <th>Показания</th>
                    <th>Сумма к оплате</th>
                </tr>
                </thead>
                <tbody>
                <#list bills as bill>
                    <tr>
                        <td>${bill.date}</td>
                        <td>${bill.meter}<#if service.type = "Электричество">
                                кВт.ч
                            <#else>
                                куб.м
                            </#if></td>
                        <td>₽ ${bill.amount}</td>
                        <td><input type="button" value="Оплатить" onClick='location.href="/address/${service.addressId.id}/service/${service.id}/payBill/${bill.id}"'></td>
                    </tr>
                <#else>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <div class="text-center">
        <input type="button" value="Ввести новые показания" onClick='location.href="/address/${service.addressId.id}/service/${service.id}/createBill/${service.type}"'>
        <input type="button" value="Назад к услугам" onClick='location.href="/address/${service.addressId.id}"'>
    </div>
</@c.page>