"use client";

import { useState } from 'react';
import axios from "axios";

export default function useSecureKeypad() {
  const [keypad, setKeypad] = useState();  // 빈 배열로 초기화
  const [userInput, setUserInput] = useState([]);
  const [image, setImage] = useState('');  // 빈 문자열로 초기화

  const getSecureKeypad = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/keypad/generate');
      const data = response.data;
      console.log("API Response:", data);
      
      if (data.keys && Array.isArray(data.keys)) {
        setKeypad(data.keys);
        setImage(data.image);
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

  const sendUserInput = (input) => {
    // 6개 입력이 완료된 후 userInput을 알림으로 표시하고, 새로운 키패드 요청
    alert(`User Input: ${input.join(' ')}`);
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
