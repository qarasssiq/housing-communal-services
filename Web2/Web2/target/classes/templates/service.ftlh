<#import "parts/common.ftlh" as c>

<@c.page "Услуга">
    <div class="row g-0">
        <form method="post">
            <div class="col-lg-8 offset-2 text-center">
                <h1 style="margin-top: 25px; margin-bottom: 25px">Счета</h1>
                <table class="table table-bordered table-hover align-middle table-striped">
                    <thead>
                    <tr>
                        <th>Номер</th>
                        <th>Дата</th>
                        <th>Показания</th>
                        <th>Сумма к оплате</th>
                        <th style="width: 275px">Подтверждение</th>
                        <th>Загрузка чека</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list bills as bill>
                        <tr>
                            <td>${bill.id}</td>
                            <td>${bill.date.format(formatter)}</td>
                            <td>${bill.meter}<#if service.type = "Электричество">
                                    кВт.ч
                                <#else>
                                    куб.м
                                </#if></td>
                            <td>₽ ${bill.amount}</td>
                            <td>
                                <label for="payAmount" style="font-weight: bold; margin-top: 10px">₽ </label>
                                <input id="payAmount" type="text" name="payAmount" style="width: 100px">
                                <input type="button" value="Подтвердить" onClick="myFunction()">
                                <script type="text/javascript">
                                    function myFunction(){
                                        var amount = document.getElementById("payAmount").value;
                                        <#--if (amount > ${bill.amount}) {-->
                                            window.alert(${bill.amount}.amount);
                                        <#--} else {-->
                                        <#--    location.href = "/address/${service.addressId.id}/service/${service.id}/confirmPayment/${bill.id}/sum=" + amount;-->
                                        <#--}-->
                                    }
                                </script>
                            </td>
                            <td><input type="file" id="file" class="file" value="Загрузить чек" name="file"/></td>
                        </tr>
                    <#else>
                    </#list>
                    </tbody>
                </table>
            </div>
        </form>
    </div>
    <div class="text-center">
        <input type="button" value="Ввести новые показания" onClick='location.href="/address/${service.addressId.id}/service/${service.id}/createBill/${service.type}"'>
        <input type="button" value="История платежей" onClick='location.href="/address/${service.addressId.id}/service/${service.id}/history"'>
        <input type="button" value="Назад к услугам" onClick='location.href="/address/${service.addressId.id}"'>
    </div>
</@c.page>