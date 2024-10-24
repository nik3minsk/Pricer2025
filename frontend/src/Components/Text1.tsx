// Text1.tsx
import React, { useState } from "react";
import { Button, TextInput } from "@mantine/core";
import CurrencySelector from './CurrencySelector';

const BACK_URL: string = import.meta.env.VITE_BACK_URL;
console.log(BACK_URL);

const Text1 = () => {
    const [value, setValue] = useState('');
    const [priceCurrencyChoice, setPriceCurrencyChoice] = useState<number | null>(null);

    const test = async () => {
        try {
            const response = await fetch(`${BACK_URL}/api/main`);
            const data = await response.json();
            console.log(data); // Выводим ответ в консоль
        } catch (e) {
            console.log(e);
        }
    };

    return (
        <>
            <TextInput
                value={value}
                onChange={(event) => setValue(event.currentTarget.value)}
            />
            <CurrencySelector onCurrencySelect={setPriceCurrencyChoice} />
            <Button onClick={() => {
                console.log(test());
                console.log('Selected currency ID:', priceCurrencyChoice);
            }}>Knopka</Button>
        </>
    );
}

export default Text1;
