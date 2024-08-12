import '../style/keypad.css';

export default function SecureKeypad({ keypad, onKeyPressed, image }) {
    if (!keypad || keypad.length === 0) {
        return <div>키패드 데이터를 불러오는 중...</div>;
    }

    return (
        <div className="keypad-container">
            <table className="table-style" style={{ backgroundImage: `url(${image})` }}>
                <tbody>
                    {Array.from({ length: 3 }).map((_, rowIndex) => (
                        <tr key={rowIndex}>
                            {Array.from({ length: 4 }).map((_, colIndex) => {
                                const keyIndex = rowIndex * 4 + colIndex;
                                const key = keypad[keyIndex];

                                return (
                                    <td key={colIndex} className="td-style">
                                        <button 
                                            className="button-style"
                                            onClick={() => onKeyPressed(key)}
                                        >
                                            <div className="number-style">
                                                {keyIndex + 1}
                                            </div>
                                        </button>
                                    </td>
                                );
                            })}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
