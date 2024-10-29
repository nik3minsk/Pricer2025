import React, { useState, useEffect } from "react";
import { Button, TextInput, Stack, Text } from "@mantine/core";
import CurrencySelector from './CurrencySelector';
import FileSelector from './FileSelector'; // Import the FileSelector component
import { useParams } from 'react-router-dom';

const BACK_URL: string = import.meta.env.VITE_BACK_URL;
console.log(BACK_URL);

const Text1 = () => {
    const { isGeneralPrice, priceNumber } = useParams<{ isGeneralPrice: string, priceNumber: string }>();
    const [value, setValue] = useState('');
    const [priceCurrencyChoice, setPriceCurrencyChoice] = useState<number | null>(null);
    const [generalNumberTest, setGeneralNumberTest] = useState('');
    const folderPath = "/Users/nik3minsk/IdeaProjects/pricer2025/xlsx"; // Set your folder path here

    useEffect(() => {
        const isGeneralPriceBool = isGeneralPrice === 'true';
        console.log('isGeneralPrice:', isGeneralPriceBool);
        console.log('priceNumber:', parseInt(priceNumber, 10));
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

    const handleFileSelect = (file) => {
        setValue(file); // Set the selected file path to your state
    };

    let localIsGeneralPrice: boolean = isGeneralPrice === 'true';
    localIsGeneralPrice = true;  // Присваиваем новое значение
    let localPriceNumber: number = parseInt(priceNumber, 10);
    localPriceNumber = 12;

    return (
        <Stack>
            <TextInput
                value={value}
                onChange={(event) => setValue(event.currentTarget.value)}
                placeholder="Выберите файл для настройки"
            />
            <FileSelector folderPath={folderPath} onSelectFile={handleFileSelect} /> {/* Add the FileSelector component */}
            <CurrencySelector onCurrencySelect={setPriceCurrencyChoice} />
            <Button onClick={() => {
                console.log(test());
                console.log('Selected currency ID:', priceCurrencyChoice);
            }}>Knopka</Button>
            {localIsGeneralPrice && (
                <TextInput
                    value={generalNumberTest}
                    onChange={(event) => setGeneralNumberTest(event.currentTarget.value)}
                    placeholder="Введите код основного прайса"
                />
            )}
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
