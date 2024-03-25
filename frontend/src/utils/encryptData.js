async function encryptData(data, publicKey) {
    // Convert the public key string to a CryptoKey object
    const importedKey = await window.crypto.subtle.importKey(
      'spki',
      publicKey,
      {
        name: 'RSA-OAEP',
        hash: { name: 'SHA-1' },
      },
      false,
      ['encrypt']
    );
  
    // Convert the data string to an ArrayBuffer
    const dataBuffer = new TextEncoder().encode(data);
  
    // Encrypt the data using the imported public key
    const encryptedData = await window.crypto.subtle.encrypt(
      {
        name: 'RSA-OAEP',
      },
      importedKey,
      dataBuffer
    );
  
    // Convert the encrypted data ArrayBuffer to a base64 string
    const encryptedDataArray = new Uint8Array(encryptedData);
    const encryptedDataString = btoa(String.fromCharCode(...encryptedDataArray));
  
    return encryptedDataString;
  }
  
  // Example usage:
  const publicKey = `-----BEGIN PUBLIC KEY-----
  MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAstWB95C5pHLXiYW59qyO
  4Xb+59KYVm9Hywbo77qETZVAyc6VIsxU+UWhd/k/YtjZibCznB+HaXWX9TVTFs9N
  wgv7LRGq5uLczpZQDrU7dnGkl/urRA8p0Jv/f8T0MZdFWQgks91uFffeBmJOb58u
  68ZRxSYGMPe4hb9XXKDVsgoSJaRNYviH7RgAI2QhTCwLEiMqIaUX3p1SAc178ZlN
  8qHXSSGXvhDR1GKM+y2DIyJqlzfik7lD14mDY/I4lcbftib8cv7llkybtjX1Aayf
  Zp4XpmIXKWv8nRM488/jOAF81Bi13paKgpjQUUuwq9tb5Qd/DChytYgBTBTJFe7i
  rDFCmTIcqPr8+IMB7tXA3YXPp3z605Z6cGoYxezUm2Nz2o6oUmarDUntDhq/PnkN
  ergmSeSvS8gD9DHBuJkJWZweG3xOPXiKQAUBr92mdFhJGm6fitO5jsBxgpmulxpG
  0oKDy9lAOLWSqK92JMcbMNHn4wRikdI9HSiXrrI7fLhJYTbyU3I4v5ESdEsayHXu
  iwO/1C8y56egzKSw44GAtEpbAkTNEEfK5H5R0QnVBIXOvfeF4tzGvmkfOO6nNXU3
  o/WAdOyV3xSQ9dqLY5MEL4sJCGY1iJBIAQ452s8v0ynJG5Yq+8hNhsCVnklCzAls
  IzQpnSVDUVEzv17grVAw078CAwEAAQ==
  -----END PUBLIC KEY-----`;
  
  const dataToEncrypt = '829432957832';
  encryptData(dataToEncrypt, publicKey).then((encryptedData) => {
    console.log('Encrypted data:', encryptedData);
  });
  