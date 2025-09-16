import {GiCook, GiHotMeal} from "react-icons/gi";
import {GrSchedule, GrScheduleNew} from "react-icons/gr";
import { GiCorn } from "react-icons/gi";
import TooltipLink from "./TooltipLink.tsx";
import {MainNavNames} from "../enums.ts";

type Props = {
    open: boolean;
}

export default function NavIcons(props: Readonly<Props>){

    return (
            <div className={`nav-links ${!props.open ? 'open' : 'open'} `}>
                <TooltipLink
                    tooltipText={ MainNavNames.Recipe }
                    to = "/recipe"
                    element={ <GiCook style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.CreateRecipe }
                    to = "/recipeplan"
                    element={ <GiHotMeal style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Ingredients }
                    to = "/ingredient"
                    element={ <GiCorn style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-6"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Weekplan }
                    to = "/weekplan"
                    element={ <GrSchedule style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Shoppinglist }
                    to = "/shoppinglist"
                    element={ <GrScheduleNew style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-6"
                />
            </div>
    );
}