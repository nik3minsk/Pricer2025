// import React from 'react';
// import { NumberInput, Stack } from "@mantine/core";
//
// interface GeneralPriceSettingsProps {
//     ownPriceColNumber: number | string;
//     setOwnPriceColNumber: (value: number | string) => void;
//     ownOnStockColNumber: number | string;
//     setOwnOnStockColNumber: (value: number | string) => void;
//     ownReservedOnStockColNumber: number | string;
//     setOwnReservedOnStockColNumber: (value: number | string) => void;
//     ownFreeOnStockColNumber: number | string;
//     setOwnFreeOnStockColNumber: (value: number | string) => void;
//     ownPriceForSiteColNumber: number | string;
//     setOwnPriceForSiteColNumber: (value: number | string) => void;
// }
//
// const AddSellerForOwnPriceGeneralPriceSettings: React.FC<GeneralPriceSettingsProps> = ({
//                                                                        ownPriceColNumber, setOwnPriceColNumber,
//                                                                        ownOnStockColNumber, setOwnOnStockColNumber,
//                                                                        ownReservedOnStockColNumber, setOwnReservedOnStockColNumber,
//                                                                        ownFreeOnStockColNumber, setOwnFreeOnStockColNumber,
//                                                                        ownPriceForSiteColNumber, setOwnPriceForSiteColNumber
//                                                                    }) => (
//     <Stack>
//         <NumberInput value={ownPriceColNumber} onChange={setOwnPriceColNumber} placeholder="Наша цена, столбец №" />
//         <NumberInput value={ownOnStockColNumber} onChange={setOwnOnStockColNumber} placeholder="Наш остаток, столбец №" />
//         <NumberInput value={ownReservedOnStockColNumber} onChange={setOwnReservedOnStockColNumber} placeholder="Наш резерв, столбец №" />
//         <NumberInput value={ownFreeOnStockColNumber} onChange={setOwnFreeOnStockColNumber} placeholder="У нас свободно, столбец №" />
//         <NumberInput value={ownPriceForSiteColNumber} onChange={setOwnPriceForSiteColNumber} placeholder="Цена для сайта, столбец №" />
//     </Stack>
// );
//
// export default AddSellerForOwnPriceGeneralPriceSettings;
