import {type ChangeEvent, type FormEvent, useCallback, useEffect, useState} from "react";
import axios, {type AxiosResponse} from "axios";
import type {Ingredient, Nutrient, Nutrients} from "../types.ts";
import IngredientLayout from "../layout/ingredient_layout.tsx";
import NutrientLayout from "../layout/nutrient_layout.tsx";
import {ingredientToDto, mapNutrientsToNutrientArray} from "../helper.ts";

export default function IngredientDetails() {
    const ingredientId: string | null = sessionStorage.getItem("detailId");
    const [ingredient, setIngredient] = useState<Ingredient>()
    const [nutrients, setNutrients] = useState<Nutrient[]>([])

    const [isLoading, setLoading] = useState<boolean>(false)

    const [isIngredientChanged, setIngredientChanged] = useState<boolean>(false)
    const [isNutrientChanged, setNutrientChanged] = useState<boolean>(false)
    const [isDataChanged, setDataChanged] = useState<string>("")

    const getDetails = useCallback( async ( id: string | null ) => {
        try {
            if (id) {
                setLoading(true)
                const responseIngredient = await axios.get("/eyf/ingredients/ingredient/detail/" + id)
                const ingredient: Ingredient = responseIngredient.data
                setIngredient(ingredient)
                if (ingredient) {
                    const responseNutrient = await axios.get<Nutrients[]>("/eyf/nutrients/detail/" + ingredient.nutrientsId);
                    setNutrients(mapNutrientsToNutrientArray(responseNutrient));
                }
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        } finally {
            setLoading(false)
        }
    }, [])
    function handleChangeNutrient(e:ChangeEvent<HTMLInputElement>) {
        e.preventDefault();
        const {name, value} = e.target
        const updateNutrients:Nutrient[] = nutrients.map((n) =>
            (n.name === name) && (n.unit !== "kJ")
                ? { ...n, quantity: Number(value) } as Nutrient
                : n
        );
        setNutrients(updateNutrients)
        setNutrientChanged(true);
        setDataChanged( "" );
    }
    function handleChangeIngredient(e:ChangeEvent<HTMLInputElement>) {
        e.preventDefault();
        const { name, value } = e.target;
        const newValue:string|number = name === "price" ? Number (value) : String (value);
        setIngredient(prevState => ( { ...prevState, [name]: newValue } as Ingredient ) );
        console.log(JSON.stringify(ingredient))
        setIngredientChanged(true);
        setDataChanged( "" );
    }

    const submit = async (e:FormEvent<HTMLFormElement>)=> {
        e.preventDefault();
        if(ingredient) {
            if ( isIngredientChanged || isNutrientChanged ) {
                setDataChanged( "" );
                if ( isIngredientChanged ) {
                    try {
                        const response = await axios.put("/eyf/ingredients/"+ingredientId, ingredientToDto(ingredient));
                        setIngredient(response.data);
                        setIngredientChanged(false);
                    } catch (error){
                        if (axios.isAxiosError(error)) {
                            console.error('Axios error:', error.response?.data || error.message);
                        } else {
                            console.error('Unexpected error:', error);
                        }
                    }
                }
                if ( isNutrientChanged ) {
                    try {
                        const response = await axios.put<AxiosResponse>("/eyf/nutrients/" + ingredient.nutrientsId, nutrients)
                        setNutrients(mapNutrientsToNutrientArray(response));
                        setNutrientChanged(false)
                    } catch (error){
                        if (axios.isAxiosError(error)) {
                            console.error('Axios error:', error.response?.data || error.message);
                        } else {
                            console.error('Unexpected error:', error);
                        }
                    }
                }
                await getDetails(ingredientId);
                setDataChanged( "Daten wurden gespeichert." )
            } else {
                setDataChanged( "Es wurde nichts geändert. Speichern abgebrochen." )
            }
        }
    }

    useEffect( () => {
        (async () => {
            await getDetails(ingredientId);
        })();
    }, [ingredientId, getDetails] );

    return (
        <>
            <div>
                { isLoading && (
                    <h1> Einen Moment bitte Zutat wird geladen ...</h1>
                ) }
                <h1 className="p-5"> Hier findest Du Informationen zu einer Zutat und <br/>kannst sie auch bearbeiten. </h1>
                <form  onSubmit={(e)=>submit(e)}>
                    { ingredient && (<IngredientLayout ingredient={ ingredient } onChange={ handleChangeIngredient }/>) }
                    { nutrients && (<NutrientLayout nutrients={ nutrients } onChange={ handleChangeNutrient }/>) }
                    { isDataChanged !== "" && (
                        <span>{ isDataChanged }</span>
                    ) }
                    <button className="border pt-2 pb-2 w-full" type="submit" >
                        Speichern
                    </button>
                </form>
            </div>
        </>
    );
}