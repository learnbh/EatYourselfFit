import {useNavigate, useParams} from "react-router-dom";
import {type ChangeEvent, type FormEvent, useCallback, useEffect, useState} from "react";
import axios from "axios";
import type {IngredientCreate, IngredientProfile, Nutrient, Nutrients} from "../types.ts";
import IngredientLayout from "../layout/ingredient_layout.tsx";
import NutrientLayout from "../layout/nutrient_layout.tsx";
import {mapNutrientsToCreateNutrientArray} from "../helper.ts";

export default function IngredientCreate(){

    const routeTo = useNavigate();

    const {product} = useParams<string>()

    const [ingredientCreate, setIngredientCreate] = useState<IngredientCreate>()
    const [nutrientArray, setNutrientArray] = useState<Nutrient[]>([])

    const [isLoading, setLoading] = useState<boolean>(false)

    const [isingredientCreateChanged, setingredientCreateChanged] = useState<boolean>(false)
    const [isNutrientChanged, setNutrientChanged] = useState<boolean>(false)
    const [isErrorDataNotChanged, setErrorDataNotChanged] = useState<string>("")

    const getNutrients = useCallback( async () => {
        try {
                setLoading(true)
                const responseNutrient = await axios.get<Nutrients[]>("/eyf/ingredients/daily/nutrients");
            setNutrientArray(mapNutrientsToCreateNutrientArray(responseNutrient));
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
        const updateNutrients:Nutrient[] = nutrientArray.map((n) =>
            (n.name === name) && (n.unit !== "kJ")
                ? { ...n, quantity: Number(value) } as Nutrient
                : n
        );
        setNutrientArray(updateNutrients)
        setNutrientChanged(true);
        setErrorDataNotChanged( "" );
    }

    function handleChangeingredientCreate(e:ChangeEvent<HTMLInputElement>) {
        e.preventDefault();
        const { name, value } = e.target;
        const newValue:string|number = name === "price" ? Number (value) : String (value);
        setIngredientCreate(prevState => ( { ...prevState, [name]: newValue } as IngredientCreate ) );
        setingredientCreateChanged(true);
        setErrorDataNotChanged( "" );
    }

    const submit = async (e:FormEvent<HTMLFormElement>)=> {
        e.preventDefault();
        if(ingredientCreate) {
            if ( isingredientCreateChanged || isNutrientChanged ) {
                setErrorDataNotChanged( "" );
                const ingredientProfile:IngredientProfile = {
                    ingredientCreate:ingredientCreate,
                    nutrientsArray:nutrientArray
                }

                try {
                    await axios.post("/eyf/ingredients", ingredientProfile,
                        {
                            headers: {
                                'Content-Type': 'application/json',
                            },
                        });
                    setingredientCreateChanged(false);
                    setNutrientChanged(false);
                } catch (error){
                    if (axios.isAxiosError(error)) {
                        console.error('Axios error:', error.response?.data || error.message);
                    } else {
                        console.error('Unexpected error:', error);
                    }
                }

                routeTo("/recipeplan");

            } else {
                setErrorDataNotChanged( "Es wurde nichts geändert. Speichern abgebrochen." )
            }
        }
    }
    useEffect(() => {
        if(product) {
            const fetchIngredientCreate = async () => {
                const newingredientCreate: IngredientCreate = {
                    product: product,
                    variation: "",
                    quantity: 100,
                    unit: "g",
                    prices: 0
                }
                return newingredientCreate
            }
            fetchIngredientCreate()
                .then((response:IngredientCreate) => setIngredientCreate(response))
                .catch((error) => console.error('Error while fetching ingredientCreate:', error))
        }
    }, [product]);

    useEffect( () => {
        (async () => {
            await getNutrients();
        })();
    }, [getNutrients] );
    return(
        <>
            <div>
                { isLoading && (
                    <h1> Einen Moment bitte Zutat wird geladen ...</h1>
                ) }
                {ingredientCreate && (
                    <h1> Hier kannst Du {ingredientCreate.product} erstellen:  </h1>
                )}
                <form  onSubmit={(e)=>submit(e)}>
                    { ingredientCreate && (<IngredientLayout ingredient={ ingredientCreate } onChange={ handleChangeingredientCreate }/>) }
                    { nutrientArray && (<NutrientLayout nutrients={ nutrientArray } onChange={ handleChangeNutrient }/>) }
                    { isErrorDataNotChanged !== "" && (
                        <span>{ isErrorDataNotChanged }</span>
                    ) }
                    <button className="border pt-2 pb-2 w-full" type="submit" >
                        Speichern
                    </button>
                </form>
            </div>
        </>
    )

}