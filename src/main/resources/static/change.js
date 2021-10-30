function countryChange() {
  let xhr = new XMLHttpRequest();
  let country = document.querySelector('#receipt-country').value;
  xhr.open('GET', '/exchange/USD' + country, true);
  xhr.send();
  xhr.onload = () => {
    if (xhr.status == 200) {
      let exchange = commaSep(xhr.response);
      document.querySelector('#exchange-rate').textContent = exchange + ' ' + country + '/USD';
    } else {
      alert("통신 실패 재 요청 해주세요");
    }
  }
}

function finalPrice() {
  let xhr = new XMLHttpRequest();
  let price = document.querySelector('#usd-count').value;
  let country = document.querySelector('#receipt-country').value;
  if (price < 1 || price > 10000 || price === '') {
    alert('숫자가 올바르지 않습니다.\n1 ~ 10000 사이 숫자를 입력해주세요.');
    return;
  }
  xhr.open('GET', '/finalPrice/USD' + country + '/' + price, true);
  xhr.send();
  xhr.onload = () => {
    if (xhr.status === 200) {
      let jsonTemp = JSON.parse(xhr.response);
      document.querySelector('#receipt-complete').style.display = 'block';
      let finalPrice = commaSep(jsonTemp.finalPrice);
      let exchange = commaSep(jsonTemp.exchange);
      document.querySelector('#exchange-rate').textContent = exchange + ' ' + country + '/USD';
      document.querySelector('#final-price').textContent = finalPrice + ' ' + country;
    } else if (xhr.status === 400) {
      alert("잘못 된 요청입니다. 다시 요청해주세요.");
    } else {
      alert("통신 실패 재 요청 해주세요");
    }
  }
}

function commaSep(temp) {
  let comma = '';
  comma = temp.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
  return comma;
}
