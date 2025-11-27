import React from "react";
import type {Ingredient, IngredientDto, IngredientProductRef, Nutrient} from "./types.ts";
import axios, {type AxiosResponse} from "axios";

export function handleKeyDownNumber (e: React.KeyboardEvent<HTMLInputElement>) {
    const allowedKeys = [
        'Backspace',
        'Tab',
        'ArrowLeft',
        'ArrowRight',
        'Delete',
        'Home',
        'End'
    ];

    const isCtrlA = e.key === 'a' && (e.ctrlKey || e.metaKey);
    const isCtrlC = e.key === 'c' && (e.ctrlKey || e.metaKey);
    const isCtrlV = e.key === 'v' && (e.ctrlKey || e.metaKey);
    const isCtrlX = e.key === 'x' && (e.ctrlKey || e.metaKey);

    if (
        allowedKeys.includes(e.key) ||
        isCtrlA || isCtrlC || isCtrlV || isCtrlX
    ) {
        return;
    }

    if (/^\d$/.test(e.key)) {
        return;
    }

    if (e.key === ',') {
        if (e.currentTarget.value.includes('.')) {
            e.preventDefault();
        }
        return;
    }

    e.preventDefault();
}

export function mapNutrientsToNutrientArray(responseNutrient:AxiosResponse):Nutrient[]{
    return Object.entries(responseNutrient.data)
        .filter(n => n[0] !== "id")
        .map(n => {
            if(isNutrient(n[1])) {
                return n[1] as unknown as Nutrient
            } else {
                throw new Error("Unexpected non-Nutrient entry in data:" + JSON.stringify(n[1]));
            }
        });
}

export function mapNutrientsToCreateNutrientArray(responseNutrient:AxiosResponse):Nutrient[]{
    return Object.entries(responseNutrient.data)
        .filter(n => n[0] !== "id")
        .map(n => {
            if(isNutrient(n[1])) {
                const nutrient:Nutrient = n[1] as unknown as Nutrient
                nutrient.quantity = 0
                return nutrient
            } else {
                throw new Error("Unexpected non-Nutrient entry in data:" + JSON.stringify(n[1]));
            }
        });
}

export function isNutrient(value: unknown): value is Nutrient {
    return typeof value === 'object' &&
        value !== null &&
        'name' in value &&
        'type' in value &&
        'quantity' in value &&
        'unit' in value;
}

export function ingredientToDto(ingredient:Ingredient):IngredientDto{
    return {
        product:ingredient.product,
        variation:ingredient.variation,
        quantity:ingredient.quantity,
        unit:ingredient.unit,
        prices:ingredient.prices,
        nutrientsId:ingredient.nutrientsId
    }
}

export function getAxiosErrorMessageForUser(error:unknown):string{
    let userErrorMessage:string;
    if ( axios.isAxiosError( error ) ) {
        if (error.response?.data.messages) {
            userErrorMessage = "Produkt darf nicht leer sein!";
        } else if (error.response?.data.error) {
            userErrorMessage = "Produkt-Variation existiert schon."
        } else {
            userErrorMessage = 'Unbekannter Fehler'
        }
    } else {
        userErrorMessage = 'Unerwarteter Fehler'
    }
    return userErrorMessage;
}
export function getAxiosErrorMessageForLog(error:unknown):string{
    let errorMessage:string;
    if ( axios.isAxiosError( error ) ) {
        errorMessage = JSON.stringify( error.response?.data.error )
            || JSON.stringify( error.message )
            || JSON.stringify( error.response?.data )
            || 'Unbekannter Fehler';
    } else {
        errorMessage = 'Unerwarteter Fehler'
    }
    return errorMessage;
}
export function handleAxiosFormError(
    error: unknown,
    formRef: React.RefObject<IngredientProductRef>,
    fieldName: string
): {
    userMessage: string;
    logMessage: string;
}
{
    if (axios.isAxiosError(error)) {
        const response = error.response?.data;
        if (response?.messages || response?.error) {
            formRef.current?.focusField(fieldName);
        }
    }
    const userMessage = getAxiosErrorMessageForUser(error);
    const logMessage = getAxiosErrorMessageForLog(error);

    return { userMessage, logMessage };
}
