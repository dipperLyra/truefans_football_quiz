var paymentForm = document.getElementById('paymentForm');
paymentForm.addEventListener('submit', payWithPaystack, false);

function payWithPaystack() {
      var handler = PaystackPop.setup({
          key: 'pk_test_d2bbd368bc37028cf44ebebaffed3479c4e86e42',
          email: document.getElementById('email').value,
          amount: document.getElementById('amount').value * 100,
          currency: 'NGN',
          callback: function(response){
              $.ajax({
                  url: 'http://localhost:8030/verify_transaction?reference='+ response.reference,
                  method: 'get',
                  success: function (response) {
                      console.log(response.data.status)
                  }
              });
          },
        onClose: function() {
              alert('Transaction was not completed, window closed.');
            },
      });
      handler.openIframe();
}
