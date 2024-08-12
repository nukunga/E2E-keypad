import '../style/keypad.css';

export default function KeypadUserInput({ userInput }) {
    return (
        <div className="input-group-style">
            {userInput && userInput.map((input, index) => (
                <div
                    key={index}
                    className={`input-char-style ${input ? 'active' : ''}`}
                ></div>
            ))}
        </div>
    );
}
