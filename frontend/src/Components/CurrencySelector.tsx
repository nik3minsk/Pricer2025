// CurrencySelector.tsx
import React, { useState, useEffect } from "react";
import { Select, Notification, Loader } from "@mantine/core";
import axios from 'axios';

const BACK_URL: string = import.meta.env.VITE_BACK_URL;
console.log(`Backend URL: ${BACK_URL}`);

interface Currency {
    id: number;
    currencyCode: number;
    currencyName: string;
}

interface CurrencySelectorProps {
    onCurrencySelect: (id: number) => void;
}

const CurrencySelector: React.FC<CurrencySelectorProps> = ({ onCurrencySelect }) => {
    const [currencies, setCurrencies] = useState<Currency[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        console.log('Component mounted');
        axios.get(`${BACK_URL}/api/currencylist`)
            .then(response => {
                console.log('Response data:', response.data);
                setCurrencies(response.data);
                setLoading(false);
            })
            .catch(err => {
                console.log('Error:', err);
                setError('Не удалось загрузить список валют');
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <Loader />;
    }

    if (error) {
        return <Notification color="red">{error}</Notification>;
    }

    return (
        <Select
            // label="* выберите валюту прайс-листа"
            placeholder="* Выберите валюту прайса"
            data={currencies.map(currency => ({
                value: currency.id.toString(),
                label: `${currency.currencyName}`,
            }))}
            onChange={(value) => onCurrencySelect(parseInt(value || '0', 10))}
        />
    );
}

export default CurrencySelector;
