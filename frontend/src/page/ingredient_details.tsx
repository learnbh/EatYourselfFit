import {useCallback, useEffect, useState} from "react";
import axios from "axios";
import type {Ingredient} from "../types.ts";
import {handleKeyDownNumber} from "../helper.ts";

export default function IngredientDetails(){
    const ingredientId :string | null = sessionStorage.getItem("detailId");
    const [ingredient, setIngredient] = useState<Ingredient>()

    const [isLoading, setLoading] = useState<boolean>(false)

    function handleChange(){

    }
    function handleKeyDownText(){

    }
    const getDetails = useCallback(async (id:string|null) => {
            try {
                if(id) {
                    setLoading(true)
                    const response = await axios.get("/eyf/ingredients/ingredient/detail/" + id)
                    setIngredient(response.data)
                }
            } catch (error){
                if (axios.isAxiosError(error)) {
                    console.error('Axios error:', error.response?.data || error.message);
                } else {
                    console.error('Unexpected error:', error);
                }
            } finally {
                setLoading(false)
            }
    },[])

    useEffect(() => {
        (async () => {
            await getDetails(ingredientId);
        })();
    }, [ingredientId, getDetails]);

    return (
        <>
            <div>
                <span>Zutatdaten:</span>
                {isLoading && (
                    <h1> Einen Moment bitte Zutat wird geladen ...</h1>
                )}
                {ingredient && (
                    Object.entries(ingredient).map(([key, value]) => {
                        if(key !== "id" && key !== "nutrientsId") {
                            if(key === "quantity" || key === "prices") {
                                return (
                                    <div key={key}>
                                        <label>{key[0].toUpperCase() + key.slice(1)}</label>
                                        <input

                                            defaultValue={value}
                                            onChange={handleChange}
                                            type="number"
                                            min="0"
                                            pattern="\d*"
                                            onKeyDown={handleKeyDownNumber}
                                        />
                                    </div>
                                );
                            } else {
                                return (
                                    <div>
                                        <label>{key[0].toUpperCase() + key.slice(1)}</label>
                                        <input
                                            defaultValue={value}
                                            onChange={handleChange}
                                            type="text"
                                            onKeyDown={handleKeyDownText}
                                        />
                                    </div>
                                );
                            }
                        }
                    }))
                }
            </div>
        </>
    );
}