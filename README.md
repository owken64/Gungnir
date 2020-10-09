# Gungnir
A Language ( that is not superior at programming)

How to use

1.Install Scala (, Java)

2.Compile files, i.e. use compile.bat
(On terminal)
> compile

3.make a script file
e.g.

var F
var m
var a
equation F = ma

4. launch Gungnir with this code
(On terminal)
> scala com.yamanogusha.scale.Launcher (script file names)
(If you give Gungnir no scripts, Gungnir launch with no matter.)

5. Input substitutions in REPL
e.g.
m = 2
a = 2
F = 4

All substitutions keep equations had been defined.

# Bonus for everyone who can read Japanese
ご参照ありがとうございます。言語「グングニル」は開発中の言語です。
名前の由来は、BUMP OF CHICKENの同名の曲から。高校1年生の頃に知った曲から名付けました。この歌詞の主人公のような人間で私もありたいです。

↓ こちらもチェック
<a href="https://www.amazon.co.jp/LIVING-DEAD-BUMP-CHICKEN/dp/B0001J0BUY/ref=pd_sbs_15_1/358-2263351-7794267?_encoding=UTF8&pd_rd_i=B0001J0BUY&pd_rd_r=06d67d2e-511c-43f0-bf11-df35d4330290&pd_rd_w=NUTb4&pd_rd_wg=191pI&pf_rd_p=74dba523-b490-4864-923d-51639f6a935f&pf_rd_r=44JJVCT3CB3S0PPKBDAR&psc=1&refRID=44JJVCT3CB3S0PPKBDAR">THE LIVING DEAD - BUMP OF CHICKEN </a>

<div id="smart-button-container">
      <div style="text-align: center;">
        <div style="margin-bottom: 1.25rem;">
          <p>賽銭箱</p>
          <select id="item-options"><option value="1円玉" price="1">1円玉 - 1 JPY</option><option value="5円玉" price="5">5円玉 - 5 JPY</option><option value="10円玉" price="10">10円玉 - 10 JPY</option><option value="50円玉" price="50">50円玉 - 50 JPY</option><option value="100円玉" price="100">100円玉 - 100 JPY</option><option value="500円玉" price="500">500円玉 - 500 JPY</option><option value="1000円札" price="1000">1000円札 - 1000 JPY</option><option value="5000円札" price="5000">5000円札 - 5000 JPY</option><option value="10000円札" price="10000">10000円札 - 10000 JPY</option></select>
          <select style="visibility: hidden" id="quantitySelect"></select>
        </div>
      <div id="paypal-button-container"></div>
      </div>
    </div>
    <script src="https://www.paypal.com/sdk/js?client-id=sb&currency=JPY" data-sdk-integration-source="button-factory"></script>
    <script>
      function initPayPalButton() {
        var shipping = 0;
        var itemOptions = document.querySelector("#smart-button-container #item-options");
    var quantity = parseInt();
    var quantitySelect = document.querySelector("#smart-button-container #quantitySelect");
    if (!isNaN(quantity)) {
      quantitySelect.style.visibility = "visible";
    }
    var orderDescription = '賽銭箱';
    if(orderDescription === '') {
      orderDescription = 'Item';
    }
    paypal.Buttons({
      style: {
        shape: 'pill',
        color: 'gold',
        layout: 'vertical',
        label: 'pay',
        
      },
      createOrder: function(data, actions) {
        var selectedItemDescription = itemOptions.options[itemOptions.selectedIndex].value;
        var selectedItemPrice = parseFloat(itemOptions.options[itemOptions.selectedIndex].getAttribute("price"));
        var tax = (0 === 0) ? 0 : (selectedItemPrice * (parseFloat(0)/100));
        if(quantitySelect.options.length > 0) {
          quantity = parseInt(quantitySelect.options[quantitySelect.selectedIndex].value);
        } else {
          quantity = 1;
        }

        tax *= quantity;
        tax = Math.round(tax * 100) / 100;
        var priceTotal = quantity * selectedItemPrice + parseFloat(shipping) + tax;
        priceTotal = Math.round(priceTotal * 100) / 100;
        var itemTotalValue = Math.round((selectedItemPrice * quantity) * 100) / 100;

        return actions.order.create({
          purchase_units: [{
            description: orderDescription,
            amount: {
              currency_code: 'JPY',
              value: priceTotal,
              breakdown: {
                item_total: {
                  currency_code: 'JPY',
                  value: itemTotalValue,
                },
                shipping: {
                  currency_code: 'JPY',
                  value: shipping,
                },
                tax_total: {
                  currency_code: 'JPY',
                  value: tax,
                }
              }
            },
            items: [{
              name: selectedItemDescription,
              unit_amount: {
                currency_code: 'JPY',
                value: selectedItemPrice,
              },
              quantity: quantity
            }]
          }]
        });
      },
      onApprove: function(data, actions) {
        return actions.order.capture().then(function(details) {
          alert('Transaction completed by ' + details.payer.name.given_name + '!');
        });
      },
      onError: function(err) {
        console.log(err);
      },
    }).render('#paypal-button-container');
  }
  initPayPalButton();
    </script>
    

