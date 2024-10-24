import React, { useState } from "react";
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/dropzone/styles.css';
import { Button, Group, Modal, NumberInput, Stack, TextInput, Title } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import axios from 'axios';

const BACK_URL = import.meta.env.VITE_BACK_URL;

const HomePage = (): React.JSX.Element => {
    const [opened, { open, close }] = useDisclosure(false);
    const [priceName, setPriceName] = useState('');
    const [pathToPrice, setPathToPrice] = useState('');
    const [currencyCode, setCurrencyCode] = useState<number | string>('');
    const [vatRate, setVatRate] = useState<number | string>('');
    const [currencyBankCoeff, setCurrencyBankCoeff] = useState<number | string>('');
    const [coeffDeliveryCost, setCoeffDeliveryCost] = useState<number | string>('');
    const [minOrderSum, setMinOrderSum] = useState<number | string>('');
    const [brandColNumber, setBrandColNumber] = useState<number | string>('');
    const [articleColNumber, setArticleColNumber] = useState<number | string>('');
    const [productCategoryColNumber, setProductCategoryColNumber] = useState<number | string>('');
    const [productNameColNumber, setProductNameColNumber] = useState<number | string>('');
    const [priceColNumber, setPriceColNumber] = useState<number | string>('');
    const [onStockColNumber, setOnStockColNumber] = useState<number | string>('');
    const [barcodeColNumber, setBarcodeColNumber] = useState<number | string>('');
    const [tnvedColNumber, setTnvedColNumber] = useState<number | string>('');
    const [ownPriceColNumber, setOwnPriceColNumber] = useState<number | string>('');
    const [ownOnStockColNumber, setOwnOnStockColNumber] = useState<number | string>('');
    const [ownReservedOnStockColNumber, setOwnReservedOnStockColNumber] = useState<number | string>('');
    const [ownFreeOnStockColNumber, setOwnFreeOnStockColNumber] = useState<number | string>('');
    const [ownPriceForSiteColNumber, setOwnPriceForSiteColNumber] = useState<number | string>('');
    const [serverResponse, setServerResponse] = useState('');

    const handleClearValues = () => {
        setPriceName('');
        setPathToPrice('');
        setCurrencyCode('');
        setVatRate('');
        setCurrencyBankCoeff('');
        setCoeffDeliveryCost('');
        setMinOrderSum('');
        setBrandColNumber('');
        setArticleColNumber('');
        setProductCategoryColNumber('');
        setProductNameColNumber('');
        setPriceColNumber('');
        setOnStockColNumber('');
        setBarcodeColNumber('');
        setTnvedColNumber('');
        setOwnPriceColNumber('');
        setOwnOnStockColNumber('');
        setOwnReservedOnStockColNumber('');
        setOwnFreeOnStockColNumber('');
        setOwnPriceForSiteColNumber('');
        setServerResponse('');
    };

    const handleSaveData = async () => {
// Проверка обязательных полей
        if (!priceName || !pathToPrice || !currencyCode || !vatRate || !brandColNumber || !articleColNumber || !productNameColNumber || !priceColNumber) {
            alert('Заполните все обязательные поля');
            return;
        }

        const data = {
            sellerDetails: {
                priceName,
                pathToPrice,
                currencyCode,
                vatRate,
                currencyBankCoeff,
                coeffDeliveryCost,
                minOrderSum
            },
            rules_for_xlsx_columns: {
                brandColNumber,
                articleColNumber,
                productCategoryColNumber,
                productNameColNumber,
                priceColNumber,
                onStockColNumber,
                barcodeColNumber,
                tnvedColNumber,
                ownPriceColNumber,
                ownOnStockColNumber,
                ownReservedOnStockColNumber,
                ownFreeOnStockColNumber,
                ownPriceForSiteColNumber
            }
        };

        try {
            const response = await axios.post(`${BACK_URL}/api/addPrice2`, data, {
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            const responseData = await response.data;
            setServerResponse(JSON.stringify(responseData, null, 2));
        } catch (error) {
            setServerResponse('Ошибка при сохранении данных');
        }
    };

    return (
        <>
            <Modal size={'auto'} opened={opened} onClose={close} title="Authentication">
                <Stack>
                    <Group grow>
                        <Title ta={"center"} w={"33%"} order={4}>Сведения о прайсе продавца</Title>
                        <Title ta={"center"} w={"33%"} order={4}>Номера столбцов для парсинга</Title>
                        <Title ta={"center"} w={"33%"} order={4}>Настройки своего прайса</Title>
                    </Group>
                    <Group align={'start'} grow>
                        <Stack>
                            <TextInput value={priceName} onChange={(event) => setPriceName(event.currentTarget.value)} placeholder="* Название прайса" required />
                            <TextInput value={pathToPrice} onChange={(event) => setPathToPrice(event.currentTarget.value)} placeholder="* Ссылка на прайс" required />
                            <NumberInput value={currencyCode} onChange={setCurrencyCode} placeholder="* Код валюты" required />
                            <NumberInput value={vatRate} onChange={setVatRate} placeholder="* Учтенный % НДС" required />
                            <NumberInput value={currencyBankCoeff} onChange={setCurrencyBankCoeff} placeholder="Коэфициент банковских потерь" />
                            <NumberInput value={coeffDeliveryCost} onChange={setCoeffDeliveryCost} placeholder="Коэфициент стоимости доставки" />
                            <NumberInput value={minOrderSum} onChange={setMinOrderSum} placeholder="Минимальная сумма заказа" />
                        </Stack>
                        <Stack>
                            <NumberInput value={brandColNumber} onChange={setBrandColNumber} placeholder="* Бренд, столбец №" required />
                            <NumberInput value={articleColNumber} onChange={setArticleColNumber} placeholder="* Артикул, столбец №" required />
                            <NumberInput value={productCategoryColNumber} onChange={setProductCategoryColNumber} placeholder="Категория, столбец №" />
                            <NumberInput value={productNameColNumber} onChange={setProductNameColNumber} placeholder="* Название, столбец №" required />
                            <NumberInput value={priceColNumber} onChange={setPriceColNumber} placeholder="* Цена, столбец №" required />
                            <NumberInput value={onStockColNumber} onChange={setOnStockColNumber} placeholder="Наличие, столбец №" />
                            <NumberInput value={barcodeColNumber} onChange={setBarcodeColNumber} placeholder="Баркод, столбец №" />
                            <NumberInput value={tnvedColNumber} onChange={setTnvedColNumber} placeholder="ТНВЭД, столбец №" />
                        </Stack>
                        <Stack>
                            <NumberInput value={ownPriceColNumber} onChange={setOwnPriceColNumber} placeholder="Наша цена, столбец №" />
                            <NumberInput value={ownOnStockColNumber} onChange={setOwnOnStockColNumber} placeholder="Наш остаток, столбец №" />
                            <NumberInput value={ownReservedOnStockColNumber} onChange={setOwnReservedOnStockColNumber} placeholder="Наш резерв, столбец №" />
                            <NumberInput value={ownFreeOnStockColNumber} onChange={setOwnFreeOnStockColNumber} placeholder="У нас свободно, столбец №" />
                            <NumberInput value={ownPriceForSiteColNumber} onChange={setOwnPriceForSiteColNumber} placeholder="Цена для сайта, столбец №" />
                        </Stack>
                    </Group>
                    <Group grow>
                        <Button onClick={handleSaveData}>Сохранить</Button>
                        <Button onClick={handleClearValues}>Очистить значения</Button>
                    </Group>
                    {serverResponse && (
                        <pre style={{ marginTop: '20px', color: 'green' }}>{serverResponse}</pre>
                    )}
                </Stack>
            </Modal>
            <Button onClick={open}>Добавить собственный прайс</Button>
        </>
    );
}

export default HomePage;
