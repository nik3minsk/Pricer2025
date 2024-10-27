import React, { useState, useEffect } from "react";
import { Button, TextInput, Stack, Text } from "@mantine/core";
import CurrencySelector from './CurrencySelector';
import { useParams } from 'react-router-dom';

const BACK_URL: string = import.meta.env.VITE_BACK_URL;
console.log(BACK_URL);

const Text1 = () => {
    const { isGeneralPrice, priceNumber } = useParams<{ isGeneralPrice: string, priceNumber: string }>();
    const [value, setValue] = useState('');
    const [priceCurrencyChoice, setPriceCurrencyChoice] = useState<number | null>(null);
    const [generalNumberTest, setGeneralNumberTest] = useState('');

    useEffect(() => {
        console.log('isGeneralPrice:', isGeneralPrice);
        console.log('priceNumber:', priceNumber);
    }, [isGeneralPrice, priceNumber]);

    const test = async () => {
        try {
            const response = await fetch(`${BACK_URL}/api/main`);
            const data = await response.json();
            console.log(data); // Выводим ответ в консоль
        } catch (e) {
            console.log(e);
        }
    };

    const localIsGeneralPrice = true;
    const localPriceNumber = 3;

    return (
        <Stack>
            <TextInput
                value={value}
                onChange={(event) => setValue(event.currentTarget.value)}
            />
            <CurrencySelector onCurrencySelect={setPriceCurrencyChoice} />
            <Button onClick={() => {
                console.log(test());
                console.log('Selected currency ID:', priceCurrencyChoice);
            }}>Knopka</Button>
            <TextInput
                value={generalNumberTest}
                onChange={(event) => setGeneralNumberTest(event.currentTarget.value)}
                placeholder="Введите код основного прайса"
            />
            <Text>
                isGeneralPrice: {localIsGeneralPrice.toString()}
            </Text>
            <Text>
                priceNumber: {localPriceNumber}
            </Text>
            <Text>
                generalNumberTest: {generalNumberTest}
            </Text>
        </Stack>
    );
}

export default Text1;
