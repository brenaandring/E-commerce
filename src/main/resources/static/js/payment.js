/**
 * Define callback function for "sq-button"
 * @param {*} event
 */
let paymentForm = new SqPaymentForm({
    applicationId: "sandbox-sq0idb-zgCfDsE9CFHRnH6DDmUGVA",
    locationId: locationId,
    inputClass: 'sq-input',
    autoBuild: false,
    // Customize the CSS for SqPaymentForm iframe elements
    inputStyles: [{
        fontSize: '16px',
        // lineHeight: '24px',
        // padding: '16px',
        placeholderColor: '#6C757C',
        // backgroundColor: 'white',
    }],
    // Initialize the credit card placeholders
    cardNumber: {
        elementId: 'sq-card-number',
        placeholder: 'Card Number'
    },
    cvv: {
        elementId: 'sq-cvv',
        placeholder: 'CVV'
    },
    expirationDate: {
        elementId: 'sq-expiration-date',
        placeholder: 'MM/YY'
    },
    postalCode: {
        elementId: 'sq-postal-code',
        placeholder: 'Zip'
    },
    // SqPaymentForm callback functions
    callbacks: {
    // callback function: cardNonceResponseReceived
    // Triggered when: SqPaymentForm completes a card nonce request

        cardNonceResponseReceived: function(errors, nonce, cardData) {
            if (errors){
                let error_html = "";
                for (let i =0; i < errors.length; i++){
                    error_html +=  errors[i].message + " </br>";
                }
                document.getElementById("error").innerHTML = error_html;
                document.getElementById('sq-creditcard').disabled = false;

                return;
            }else{
                document.getElementById("error").innerHTML = "";
            }

            // Assign the nonce value to the hidden form field
            document.getElementById('card-nonce').value = nonce;

            // POST the nonce form to the payment processing page
            document.getElementById('nonce-form').submit();

        },
    }
});

paymentForm.build();

function onGetCardNonce(event) {
    // Don't submit the form until SqPaymentForm returns with a nonce
    event.preventDefault();
    // Request a nonce from the SqPaymentForm object
    paymentForm.requestCardNonce();
}
