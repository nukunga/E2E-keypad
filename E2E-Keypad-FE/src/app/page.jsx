"use client";

import React, { useEffect } from 'react';
import useSecureKeypad from '../hooks/useSecureKeypad';
import SecureKeypad from "../components/SecureKeypad";
import KeypadUserInput from "../components/KeypadUserInput.jsx";

export default function Page() {
  const { states, actions } = useSecureKeypad();

  useEffect(() => {
    if (!states.keypad) {
      actions.getSecureKeypad();
    }
  }, [actions, states.keypad]);  // actions와 states.keypad를 의존성 배열에 포함

  if (!states.keypad) {
    return (
      <div>
        ...isLoading...
      </div>
    );
  }

  return (
    <div>
      <KeypadUserInput userInput={states.userInput} />
      <SecureKeypad 
        keypad={states.keypad} 
        onKeyPressed={actions.onKeyPressed} 
        image={states.image}  // 이미지를 props로 전달
      />
    </div>
  );
}
