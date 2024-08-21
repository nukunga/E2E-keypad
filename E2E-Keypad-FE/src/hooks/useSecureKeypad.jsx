"use client";

import { useState } from 'react';
import axios from "axios";
import JSEncrypt from 'jsencrypt';

export default function useSecureKeypad() {
  const [keypad, setKeypad] = useState();  // 빈 배열로 초기화
  const [userInput, setUserInput] = useState([]);
  const [image, setImage] = useState('');  // 빈 문자열로 초기화
  const [keypad_id, setKeypadId] = useState([]);
  const [timeStamp, setTimeStamp] = useState([]);

  const getSecureKeypad = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/keypad/generate');
      const data = response.data;
      console.log("API Response:", data);
      
      if (data.keys && Array.isArray(data.keys)) {
        setKeypad(data.keys);
        setImage(data.image);
        setKeypadId(data.keypad_id);
        setTimeStamp(data.keyboardTimestamp);
        console.log("API Response_key:", data.keys);
        console.log("API Response_image:", data.image);
      } else {
        console.error('Invalid response structure:', data);
      }
    } catch (error) {
      console.error('Failed to fetch keypad data:', error);
    }
  };
  
  const onKeyPressed = (key) => {
    setUserInput((prevInput) => {
      const updatedInput = [...prevInput, key];
 
      // 키 입력이 6번 되면 sendUserInput 호출
      if (updatedInput.length === 6) {
        sendUserInput(updatedInput);
      }

      return updatedInput;
    });
    console.log(`Key Pressed: ${key}`);
  };

  const NodeRSA = require('node-rsa');

  // 공개 키 설정
  const PUBLIC_KEY = `-----BEGIN PUBLIC KEY-----
  MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtkLA7dcyLqz4M6BS/XZi
  wMee85fjwskmxfZVN/qI854Sa4mlU/5Rse0HcNY0QoF+J3kQF3xWpTKLfw2p5pzt
  sALLN6gsO2m4qLIOk3eNR+hVL2Rh4dc8MAhuXfoTGrfMjXouiy05rYgVpqIRRCjz
  MVGYnJ7arZ6rMN73nRxd0I9RVbe3LXEuHrBysxjfXae6z+qb+1Rp9MKnwiDuKC/i
  2lqqqmV9p/8OuY+qUzsMCtU8URS8kvw/bkg90TEOHzjKWrRIYRcQQkdJ8KuX3/lV
  1jBBgIQRfmQVTFUnkV5XBZw9jXYTsz6Bcp4MNWUlwHQIebAM8vMZ6/nH9p4OdETA
  5wIDAQAB
  -----END PUBLIC KEY-----`;

  const encrypt = new JSEncrypt();
  encrypt.setPublicKey(PUBLIC_KEY)
  
  const sendUserInput = async (input) => {
      // 6개 입력이 완료된 후 userInput을 알림으로 표시하고, 새로운 키패드 요청
      alert(input.join(''));
      console.log("Input data:", input.join(''));
      const encryptedUserInput = encrypt.encrypt(input.join('') || "");
      console.log("Enc data:", encryptedUserInput);
      const data = {
          userInput: encryptedUserInput,  // 암호화된 데이터 사용
          keypad_id: keypad_id,
          timeStamp: timeStamp,
      };
  
      const response = await axios.post('http://localhost:8080/api/keypad/keyValidate', data);
  
      getSecureKeypad();  // 새 키패드를 요청
      setUserInput([]);   // 입력 초기화
  };

  return {
    states: {
      keypad,
      userInput,
      image,
    },
    actions: {
      getSecureKeypad,
      onKeyPressed,
    }
  };
}
