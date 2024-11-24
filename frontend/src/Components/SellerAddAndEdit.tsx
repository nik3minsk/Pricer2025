import React, {useEffect, useState} from "react";
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/dropzone/styles.css';
import { Button, Group, Modal, NumberInput, Stack, TextInput, Title } from "@mantine/core";
import CurrencySelector from './CurrencySelector';
import axios from 'axios';
import FileSelector from "./FileSelector.tsx";

const BACK_URL = import.meta.env.VITE_BACK_URL;

interface PriceAddAndEditProps {
    isGeneralPrice: boolean;
    priceNumber: number;
    opened: boolean;
    close: () => void;
    setServerResponse: (response: string) => void;
    setResponseType: (type: string) => void;
    selectedSellerId?: number,
}

export interface PriceSettings {
    priceName: string,
    pathToPrice: string,
    currencyCode: number,
    vatRate: number,
    currencyBankCoeff: number,
    coeffDeliveryCost: number,
    minOrderSum: number,
    brandColNumber: number,
    articleColNumber: number,
    productCategoryColNumber: number,
    productNameColNumber: number,
    priceColNumber: number,
    onStockColNumber: number,
    barcodeColNumber: number,
    tnvedColNumber: number,
    ownPriceColNumber: number,
    ownOnStockColNumber: number,
    ownReservedOnStockColNumber: number,
    ownFreeOnStockColNumber: number,
    ownPriceForSiteColNumber: number,
    headerRowNumber: number,
    startPriceRowNumber: number,
}

function transformToPriceSettings(source: any): PriceSettings {
    return {
        priceName: source.priceName,
        pathToPrice: source.pathToPrice,
        currencyCode: source.economicRules.currencyCode.currencyCode,
        vatRate: source.economicRules.vatPercentInPrice,
        currencyBankCoeff: source.economicRules.currencyCoefficient,
        coeffDeliveryCost: source.economicRules.deliveryCoefficient,
        minOrderSum: source.economicRules.minOrderSum,
        brandColNumber: source.xlsPriceRules.columnBrand,
        articleColNumber: source.xlsPriceRules.columnArticle,
        productCategoryColNumber: source.xlsPriceRules.columnProductCategory,
        productNameColNumber: source.xlsPriceRules.columnProductName,
        priceColNumber: source.xlsPriceRules.columnPrice,
        onStockColNumber: source.xlsPriceRules.columnOnStock,
        barcodeColNumber: source.xlsPriceRules.columnBarcode,
        tnvedColNumber: source.xlsPriceRules.columnTnved,
        ownPriceColNumber: source.xlsPriceRules.columnPriceOnStockOwn,
        ownOnStockColNumber: source.xlsPriceRules.columnOnStockOwn,
        ownReservedOnStockColNumber: source.xlsPriceRules.columnReservedOnStockOwn,
        ownFreeOnStockColNumber: source.xlsPriceRules.columnFreeOnStock,
        ownPriceForSiteColNumber: source.xlsPriceRules.columnPriceForSiteOwn,
        headerRowNumber: source.xlsPriceRules.headerStringNumber,
        startPriceRowNumber: source.xlsPriceRules.startPriceDataRowNumber
    };
}


const SellerAddAndEdit: React.FC<PriceAddAndEditProps> = ({ isGeneralPrice, priceNumber, opened, close, setServerResponse, setResponseType, selectedSellerId  }) => {
    let priceSettings = undefined
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${BACK_URL}/api/sellers/${selectedSellerId}`);
                priceSettings = transformToPriceSettings(response.data)
                // Update state or perform other actions with the data
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();

        // Optional: Return a cleanup function if needed
        return () => {
            // Cleanup logic here
        };
    }, []); // dependencies array

    const [priceName, setPriceName] = useState(priceSettings?.priceName || '');
    const [pathToPrice, setPathToPrice] = useState(priceSettings?.pathToPrice || '');
    const [currencyCode, setCurrencyCode] = useState<number | string>(priceSettings?.currencyCode || '');
    const [vatRate, setVatRate] = useState<number | string>(priceSettings?.vatRate || '');
    const [currencyBankCoeff, setCurrencyBankCoeff] = useState<number | string>(priceSettings?.currencyBankCoeff || '');
    const [coeffDeliveryCost, setCoeffDeliveryCost] = useState<number | string>(priceSettings?.coeffDeliveryCost || '');
    const [minOrderSum, setMinOrderSum] = useState<number | string>(priceSettings?.minOrderSum || '');
    const [brandColNumber, setBrandColNumber] = useState<number | string>(priceSettings?.brandColNumber || '');
    const [articleColNumber, setArticleColNumber] = useState<number | string>(priceSettings?.articleColNumber || '');
    const [productCategoryColNumber, setProductCategoryColNumber] = useState<number | string>(priceSettings?.productCategoryColNumber || '');
    const [productNameColNumber, setProductNameColNumber] = useState<number | string>(priceSettings?.productNameColNumber || '');
    const [priceColNumber, setPriceColNumber] = useState<number | string>(priceSettings?.priceColNumber || '');
    const [onStockColNumber, setOnStockColNumber] = useState<number | string>(priceSettings?.onStockColNumber || '');
    const [barcodeColNumber, setBarcodeColNumber] = useState<number | string>(priceSettings?.barcodeColNumber || '');
    const [tnvedColNumber, setTnvedColNumber] = useState<number | string>(priceSettings?.tnvedColNumber || '');
    const [ownPriceColNumber, setOwnPriceColNumber] = useState<number | string>(priceSettings?.ownPriceColNumber || '');
    const [ownOnStockColNumber, setOwnOnStockColNumber] = useState<number | string>(priceSettings?.ownOnStockColNumber || '');
    const [ownReservedOnStockColNumber, setOwnReservedOnStockColNumber] = useState<number | string>(priceSettings?.ownReservedOnStockColNumber || '');
    const [ownFreeOnStockColNumber, setOwnFreeOnStockColNumber] = useState<number | string>(priceSettings?.ownFreeOnStockColNumber || '');
    const [ownPriceForSiteColNumber, setOwnPriceForSiteColNumber] = useState<number | string>(priceSettings?.ownPriceForSiteColNumber || '');
    console.log(priceSettings)
    const [headerRowNumber, setHeaderRowNumber] = useState<number | string>(priceSettings?.headerRowNumber || '');
    const [startPriceRowNumber, setStartPriceRowNumber] = useState<number | string>(priceSettings?.startPriceRowNumber || '');


    const handleClearValues = () => {
        setPriceName('');
        setPathToPrice('');
        setCurrencyCode('');
        setVatRate('');
        setCurrencyBankCoeff('');
        setCoeffDeliveryCost('');
        setMinOrderSum('');


        setStartPriceRowNumber('');
        setHeaderRowNumber('');

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
        if (!priceName || !pathToPrice || !currencyCode || typeof vatRate === 'undefined' || !brandColNumber || !articleColNumber || !productNameColNumber || !priceColNumber || !startPriceRowNumber) {
            alert('Заполните все обязательные поля');
            return;
        }

        const data = {
            // isGeneralPrice,
            priceNumber,
            sellerDetails: {
                isGeneralPrice,
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
                ownPriceForSiteColNumber,
                startPriceRowNumber,
                headerRowNumber,
            }
        };
        console.log(data)
        try {
            const response = await axios.post(`${BACK_URL}/api/addSeller`, data, {
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            const responseData = await response.data;
            // setServerResponse(responseData);
            setServerResponse('Прайс добавлен'); // Устанавливаем сообщение
            setResponseType('success');
            close(); // Закрыть модальное окно после успешного сохранения
        } catch (error) {
            setServerResponse('Ошибка при сохранении данных');
            setResponseType('error');
        }
    };

    const folderPath = "/Users/nik3minsk/IdeaProjects/pricer2025/xlsx"; // Set your folder path here
    const handleFileSelect = (file:any) => {
        setPathToPrice(file);
        // setValue(file); // Set the selected file path to your state

    };

    return (
        <Modal size={'auto'} opened={opened} onClose={close} title="Authentication" centered className="modalCentered">
            <Stack>
                <Group grow>
                    <Title ta={"center"} w={"33%"} order={4}>Сведения о прайсе продавца</Title>
                    <Title ta={"center"} w={"33%"} order={4}>Номера столбцов для парсинга</Title>
                    { isGeneralPrice && <Title ta={"center"} w={"33%"} order={4}>Настройки своего прайса</Title> }
                </Group>
                <Group align={'start'} grow>
                    <Stack>
                        <TextInput value={priceName} onChange={(event) => setPriceName(event.currentTarget.value)} placeholder="* Название прайса" required />

                        {/*<TextInput value={pathToPrice} onChange={(event) => setPathToPrice(event.currentTarget.value)} placeholder="* Ссылка на прайс" required />*/}

                        {/*файлы выбирает, но не передает значение*/}
                        {/*<FileSelector folderPath={folderPath} onSelectFile={handleFileSelect} /> /!* Add the FileSelector component *!/*/}
                        <FileSelector folderPath={folderPath} onSelectFile={handleFileSelect} /> {/* Add the FileSelector component */}

                        <CurrencySelector onCurrencySelect={setCurrencyCode} />
                        <NumberInput value={vatRate} onChange={setVatRate} placeholder="* Учтенный % НДС" required />
                        <NumberInput value={currencyBankCoeff} onChange={setCurrencyBankCoeff} placeholder="Коэфициент банковских потерь" />
                        <NumberInput value={coeffDeliveryCost} onChange={setCoeffDeliveryCost} placeholder="Коэфициент стоимости доставки" />
                        {/*<NumberInput value={minOrderSum} onChange={setMinOrderSum} placeholder="Минимальная сумма заказа" />*/}
                    </Stack>
                    <Stack>
                        <NumberInput value={startPriceRowNumber} onChange={setStartPriceRowNumber} placeholder="* Начало прайса, строка №" required />
                        <NumberInput value={headerRowNumber} onChange={setHeaderRowNumber} placeholder="* Заголовок, строка №" required />
                        <NumberInput value={brandColNumber} onChange={setBrandColNumber} placeholder="* Бренд, столбец №" required />
                        <NumberInput value={articleColNumber} onChange={setArticleColNumber} placeholder="* Артикул, столбец №" required />
                        <NumberInput value={productCategoryColNumber} onChange={setProductCategoryColNumber} placeholder="Категория, столбец №" />
                        <NumberInput value={productNameColNumber} onChange={setProductNameColNumber} placeholder="* Название, столбец №" required />
                        <NumberInput value={priceColNumber} onChange={setPriceColNumber} placeholder="* Цена, столбец №" required />
                        <NumberInput value={onStockColNumber} onChange={setOnStockColNumber} placeholder="Наличие, столбец №" />
                        <NumberInput value={barcodeColNumber} onChange={setBarcodeColNumber} placeholder="Баркод, столбец №" />
                        <NumberInput value={tnvedColNumber} onChange={setTnvedColNumber} placeholder="ТНВЭД, столбец №" />
                    </Stack>
                    {isGeneralPrice && (
                        <Stack>
                            <NumberInput value={ownPriceColNumber} onChange={setOwnPriceColNumber} placeholder="Наша цена, столбец №" />
                            <NumberInput value={ownOnStockColNumber} onChange={setOwnOnStockColNumber} placeholder="Наш остаток, столбец №" />
                            <NumberInput value={ownReservedOnStockColNumber} onChange={setOwnReservedOnStockColNumber} placeholder="Наш резерв, столбец №" />
                            <NumberInput value={ownFreeOnStockColNumber} onChange={setOwnFreeOnStockColNumber} placeholder="У нас свободно, столбец №" />
                            <NumberInput value={ownPriceForSiteColNumber} onChange={setOwnPriceForSiteColNumber} placeholder="Цена для сайта, столбец №" />
                        </Stack>
                    )}
                </Group>
                <Group grow>
                    <Button onClick={handleSaveData}>Сохранить</Button>
                    <Button onClick={handleClearValues}>Очистить значения</Button>
                </Group>
            </Stack>
        </Modal>
    );
};

export default SellerAddAndEdit;