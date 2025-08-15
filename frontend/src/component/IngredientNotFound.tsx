import React from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import type {Ingredient} from "../types.ts";

type Props = {
    searchWord:string
    setSearchWord: (search:string) => void
    setSearchResult: (result: Ingredient[]) => void
    setSearchNotFoundVisible: (searchNotFoundVisible: boolean) => void
    setIsLoading: (isLoading: boolean) => void
}
export default function IngredientNotFound(props:Readonly<Props>) {

    const routeTo =useNavigate()

    const { searchWord, setSearchWord, setSearchResult, setIsLoading, setSearchNotFoundVisible } = props;

    const editIngredientGeneratedByOpenAi = async (search:string) => {
        if(search.trim() === ""){
            setSearchResult([]);
            setIsLoading(false);
            setSearchNotFoundVisible(false);
            return;
        }
        try {
            setIsLoading(true);
            const response = await axios.get("/eyf/ingredients/name/" + search);
            if (response.data.length > 0) {
                const ingredientId:string = response.data[0].id;
                setSearchResult(response.data);
                sessionStorage.setItem('detailId',ingredientId);
                routeTo("/ingredient/" + ingredientId);
                setSearchNotFoundVisible(false);
            } else {
                setSearchResult([]);
                setSearchNotFoundVisible(true);
            }
        } catch (error){
            if (axios.isAxiosError(error)) {
                if (error.response?.data.message) {
                    console.error('Axios errors:', error.response?.data || error.message);
                } else {
                    console.error('Unexpected Axios error:', error);
                }
            } else {
                console.log(error);
            }
        }finally {
            setIsLoading(false);
        }
    };

    const addIngredientPerOpenAi = async () => {
        try {
            setSearchNotFoundVisible(false);
            setIsLoading(true);
            await axios.post("/eyf/ingredients/openai/add", {product: searchWord, variation: ""})
            await editIngredientGeneratedByOpenAi(searchWord);
        } catch (error) {
            if (axios.isAxiosError(error)) {
                if (error.response?.data.message) {
                    console.error('Axios errors:', error.response?.data || error.message);
                } else {
                    console.log("error: " + error);
                    alert('Es konnten keine Nährstoffe gefunden werden. Änderne die Anfrage und versuche es erneut.');
                }
            } else {
                console.log(error);
            }
        } finally {
            setIsLoading(false);
        }
    }
    function abortAddIngredient (){
        setSearchWord("");
        setSearchResult([]);
        //setSearchNotFoundVisible(false);
    }

    function addPerUser(){
        routeTo("/ingredient/add/"+searchWord);
    }
    function handleAddPerUser(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        addPerUser();
    }
    function handleAbort(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault()
        abortAddIngredient();
    }
    return (
        <>
            <div className="flex flex-col justify-items-start gap-4 w-full pt-2 border">
                <span>Zutat ist noch nicht in der Datenbank vorhanden!
                    <br/>
                    Möchtest Du die Zutat selber oder über KI hinzufügen?
                </span>
                <div className="grid grid-cols-4 items-center gap-4 w-full border p-2">
                    <button
                        className=" border"
                        onClick={addIngredientPerOpenAi}
                    >
                        KI
                    </button>
                    <button
                        className=" border"
                        onClick={handleAddPerUser}
                    >
                        Ich
                    </button>
                    <button
                        className="col-span-2 border"
                        onClick={handleAbort}
                    >
                        Abbrechen
                    </button>
                </div>
            </div>
        </>
    )
}